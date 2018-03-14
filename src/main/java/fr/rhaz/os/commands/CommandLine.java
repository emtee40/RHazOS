package fr.rhaz.os.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandLine {
	private String[] line;
	private ArrayList<String> list;
	private List<?> args;
	
	public CommandLine(String[] line, Argument<?>[] args) {
		this.line = line;
		this.args = new ArrayList<>(new ArrayList<>(Arrays.asList(args)).stream().map(
				new Function<Argument<?>, Class<? extends Argument<?>>>() {
					@SuppressWarnings("unchecked")
					@Override
					public Class<? extends Argument<?>> apply(Argument<?> arg) {
						return (Class<? extends Argument<?>>) arg.getClass();
					}
		}).collect(Collectors.toList()));
		this.list = new ArrayList<>(Arrays.asList(line));
	}
	
	public String[] getRawLine() {
		return line;
	}
	
	public <T> Optional<T> read(Argument<T> arg) throws ExecutionException{
		
		int i = args.indexOf(arg.getClass());
		
		if(i == -1) return Optional.ofNullable(null);
		
		String value;
		try{
			value = list.get(i);
		} catch (IndexOutOfBoundsException e) {
			return Optional.ofNullable(null);
		}
		
		T object = arg.get(value);
		args.remove(i);
		list.remove(i);
		return Optional.ofNullable(object);
		
		
		
		/*T object = null;
		
		Stream<String> stream = list.stream().filter((value) -> {
			try {
				arg.check(value);
				return true;
			} catch(ArgumentException ae) {
				return false;
			}
		});
		
		Optional<String> optional = stream.findFirst();
		stream.close();
		
		try {
			if(optional.isPresent()) {
				object = arg.get(optional.get());
				list.remove(object);
			}
		} catch (ExecutionException e1) {}

		return Optional.ofNullable(object);*/
	}

}
