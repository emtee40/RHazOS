package fr.rhaz.os.commands.permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import fr.rhaz.os.Utils;

public class PermissionManager {
	
	public static Permission all = new Permission("*");
	
	public static HashSet<Permission> merge(Collection<Collection<Permission>> lists){
		HashSet<Permission> permissions = new HashSet<>();
		for(Collection<Permission> list:lists)
			permissions.addAll(list);
		return calculate(permissions);
	}
	
	public static HashSet<Permission> calculate(Permission... array){
		return calculate(Utils.list(array));
	}
	
	public static ArrayList<Permission> from(Collection<String> list){
		ArrayList<Permission> perms = new ArrayList<>();
		for(String str:list)
			perms.add(new Permission(str));
		
		return perms;
	}
	
	public static ArrayList<Permission> from(String... list){
		ArrayList<Permission> perms = new ArrayList<>();
		for(String str:list)
			perms.add(new Permission(str));
		
		return perms;
	}
	
	public static HashSet<Permission> calculate(Collection<Permission> list){
		HashSet<Permission> permissions = new HashSet<>();
		for(Permission permission:list) {
			
			if(permission.isNegative()) {
				Permission base = permission.getBase();
				if(permissions.contains(base))
					permissions.remove(base);
			}
			
			if(permission.isAll()) {
				final Permission parent = permission.getParent();
				permissions.removeIf(new Predicate<Permission>() {
					@Override
					public boolean test(Permission p) {
						return p.name().startsWith(parent+".");
					}
				}
				);
			}
			
			if(!permission.isNegative()) 
				permissions.add(permission);
			
		}
		return permissions;
	}
	
	public static boolean has(HashSet<Permission> permissions, Permission perm) {
		
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
