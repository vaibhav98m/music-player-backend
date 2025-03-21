package com.spotify.music.player.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.music.player.Exception.CustomExcetion;
import com.spotify.music.player.annotation.SkipLogging;
import com.spotify.music.player.entity.AlbumEntity;
import com.spotify.music.player.entity.SongEntity;
import com.spotify.music.player.model.AlbumModel;
import com.spotify.music.player.model.SongDetails;

@Component
public class Util {

	public List<SongDetails> songDetailsMapper(List<SongEntity> songDetailsList) {
		List<SongDetails> songs = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		if (!songDetailsList.isEmpty()) {
			songs = songDetailsList.stream().map(song -> {
				try {
					song.setTitle(regexAlbumName(song.getTitle()));
					song.setAlbum(regexAlbumName(song.getAlbum()));
					song.setFilePath(null);
					return mapper.readValue(mapper.writeValueAsString(song), SongDetails.class);
				} catch (JsonProcessingException e) {
					throw new CustomExcetion(e.getMessage(), "DB01");
				}
			}).collect(Collectors.toList());
		}

		return songs;
	}

	public List<AlbumModel> getAllAlbumsMapper(List<AlbumEntity> albumEntityList) {

		List<AlbumModel> albums = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		if (!albumEntityList.isEmpty()) {
			albums = albumEntityList.stream().map(album -> {
				try {
					return mapper.readValue(mapper.writeValueAsString(album), AlbumModel.class);
				} catch (JsonProcessingException e) {
					throw new CustomExcetion(e.getMessage(), "DB01");
				}
			}).collect(Collectors.toList());
		}

		return albums;

	}

	@SkipLogging
	public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = new HashSet<>();
		return t -> seen.add(keyExtractor.apply(t));
	}

	public <T> List<T> getSubList(List<T> list, int offset, int limit) {
		if (list == null) {
			throw new IllegalArgumentException("List cannot be null");
		}
		if (offset < 0 || limit < 0 || offset > list.size()) {
			throw new IllegalArgumentException("Invalid offset or limit");
		}

		int startIndex = Math.min(limit * (offset - 1), list.size());

		int endIndex = Math.min(limit * offset, list.size());
		return list.subList(startIndex, endIndex);
	}
	
	@SkipLogging
	public String regexAlbumName(String input) {

		// Regular expression to match the year in brackets
		String result = input.replace("Atif Aslam - ", "").replaceAll("www.songs.pk", "").replaceAll("\\s*\\(\\d{4}\\)|\\s*-.*$", "")
				.replaceAll("::Singamda.Com::", "");

		return result;
	}


}
