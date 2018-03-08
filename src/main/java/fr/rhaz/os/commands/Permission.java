package fr.rhaz.os.commands;

public class Permission {
	
	private String name;
	private Permission[] required;

	public Permission(String name) {
		this(name, new Permission[] {});
	}
	
	public Permission(String name, Permission... required) {
		this.name = name;
		this.required = required;
	}
	
	public boolean requires() {
		return required.length == 0? false:true;
	}
	
	public Permission[] required() {
		return this.required;
	}
	
	public String name() {
		return name;
	}
	
	public boolean isChild() {
		return name().split(".").length > 1;
	}
	
	public boolean isAll() {
		return name().endsWith(".*");
	}
	
	public Permission getParent() {
		String[] split = name().split(".");
		String pname = "";
		for (int i = 0; i < (split.length-1); i++) {
			if(i==0) pname = split[i];
			else pname += "." + split[i];
		}
		return new Permission(pname);	
	}
	
	public String toString() {
		return name;
	}
	
	public Permission getAll() {
		return new Permission(name()+".*");
	}
	
	public boolean isNegative() {
		return name().startsWith("-");
	}
	
	public Permission getBase() {
		if(isNegative()) 
			return new Permission(name().substring(1));
		else return this;
	}
}
