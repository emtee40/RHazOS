package fr.rhaz.os.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class PermissionManager {
	
	public Permission all = new Permission("*");
	
	public HashSet<Permission> merge(Collection<Collection<Permission>> lists){
		HashSet<Permission> permissions = new HashSet<>();
		for(Collection<Permission> list:lists)
			permissions.addAll(list);
		return calculate(permissions);
	}
	
	public HashSet<Permission> calculate(Permission... array){
		return calculate(Arrays.asList(array));
	}
	
	public HashSet<Permission> calculate(Collection<Permission> list){
		HashSet<Permission> permissions = new HashSet<>();
		for(Permission permission:list) {
			
			if(permission.isNegative()) {
				Permission base = permission.getBase();
				if(permissions.contains(base))
					permissions.remove(base);
			}
			
			if(permission.isAll()) {
				Permission parent = permission.getParent();
				permissions.removeIf((p) -> 
					p.name().startsWith(parent+".")
				);
			}
			
			if(!permission.isNegative()) 
				permissions.add(permission);
			
		}
		return permissions;
	}
	
	public boolean has(HashSet<Permission> permissions, Permission perm) {
		
		if(permissions.contains(all))
			return true;
		
		all:{
			if(perm.isChild())
				if(has(permissions, perm.getParent().getAll()))
					break all;
			
			if(!permissions.contains(perm))
				return false;
		}
		
		requires:{
			if(!perm.requires()) break requires;
			
			for(Permission required:perm.required()) {
				if(!has(permissions, required))
					return false;
			}
		}
		
		return true;
	}
	
}
