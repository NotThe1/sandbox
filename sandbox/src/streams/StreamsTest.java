package streams;

import java.util.*;

public class StreamsTest {
	static List<String> genre = new ArrayList<String>(Arrays.asList("rock", "pop", "jazz", "reggae", "pop"));

	public static void main(String[] args) {
		doIt3();
		// doIt2();
		// doIt1();

	}// main

	public static void doIt3() {
		Map<String, List<String>> artists = new HashMap<String, List<String>>();
		
		artists.put("rock", new ArrayList<String>(Arrays.asList("rockArtistA", "rockArtistB")));
		artists.put("pop", new ArrayList<String>(Arrays.asList("popArtistA", "popArtistB")));
		artists.put("jazz", new ArrayList<String>(Arrays.asList("jazzArtistA", "jazzArtistB")));
		artists.put("reggae", new ArrayList<String>(Arrays.asList("reggaeArtistA", "reggaerockArtistB")));
		
		genre.stream().flatMap(s -> artists.get(s).stream()).forEach(s -> System.out.print(" " + s));
		// prints rockArtistA rockArtistB popArtistA popArtistB jazzArtistA jazzArtistB
		//reggaeArtistA reggaerockArtistB popArtistA popArtistB	}// doIt3;
	}
	public static void doIt2() {
		System.out.println(genre.stream().peek(s -> System.out.println(s)).anyMatch(s -> s.indexOf("r") == 0));
		System.out.println(genre.stream().peek(s -> System.out.println(s)).count());
	}// doIt2;

	public static void doIt1() {
		long a = genre.stream().filter((s) -> s.startsWith("r")).count();
		System.out.println(a);
	}// doIt1;

}// class StreamsTest
