package com.spotify.music.player.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spotify.music.player.entity.SongEntity;
import com.spotify.music.player.model.SongByGenreDetails;
import com.spotify.music.player.model.SongByYearDetails;
import com.spotify.music.player.model.SongDetails;
import com.spotify.music.player.repository.SongRepository;
import com.spotify.music.player.utility.Util;

@Service
public class SongsServiceImpl implements SongsService {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private Util util;

	@Override
	public SongDetails getSongDetailsById(Integer id) {
		SongDetails songDetails = new SongDetails();

		Optional<SongEntity> song = songRepository.findById(id);
		if (song.isPresent()) {

			BeanUtils.copyProperties(song.get(), songDetails);
			songDetails.setTitle(util.regexAlbumName(song.get().getTitle()));
		}
		return songDetails;
	}

	@Override
	public List<SongDetails> getAllSongDetails(Integer limit, Integer offset) {

		Pageable pageable = PageRequest.of(offset - 1, limit);
		Page<SongEntity> songDetailsList = songRepository.findAll(pageable);

		List<SongDetails> songs = new ArrayList<>();

		if (!songDetailsList.isEmpty()) {
			songs = util.songDetailsMapper(songDetailsList.getContent());
			
		}

		return songs;
	}

	@Override
	public List<String> getAllYears() {
		List<SongEntity> songDetailsList = songRepository.findAll();

		List<String> getAllYearsResponse = new ArrayList<>();

		if (!songDetailsList.isEmpty()) {
			songDetailsList = songDetailsList.stream().filter(util.distinctByKey(SongEntity::getYear))
					.collect(Collectors.toList());

			getAllYearsResponse = songDetailsList.stream().map(song -> song.getYear()).collect(Collectors.toList());

			Collections.sort(getAllYearsResponse);

		}
		return getAllYearsResponse;
	}

	@Override
	public SongByYearDetails getSongByYears(String year, Integer limit, Integer offset) {
		List<SongEntity> songDetailsList = songRepository.findByYear(year);

		SongByYearDetails response = new SongByYearDetails();

		List<SongDetails> songsList = new ArrayList<>();

		if (!songDetailsList.isEmpty()) {

			songsList = util.songDetailsMapper(songDetailsList);
			songsList = util.getSubList(songsList, offset, limit);
			response.setYear(year);
			response.setSongs(songsList);

		}
		return response;
	}

	@Override
	public List<String> getAvailableGenre() {
		List<SongEntity> songDetailsList = songRepository.findAll();

		List<String> getAllGenreResponse = new ArrayList<>();

		if (!songDetailsList.isEmpty()) {
			songDetailsList = songDetailsList.stream().filter(util.distinctByKey(SongEntity::getGenre))
					.collect(Collectors.toList());

			getAllGenreResponse = songDetailsList.stream().map(song -> song.getGenre()).collect(Collectors.toList());

			Set<String> genreSet = getAllGenreResponse.stream().flatMap(genre -> {
				String[] parts = genre.contains("/") ? genre.split("/") : new String[] { genre };
				return Arrays.stream(parts);
			}).collect(Collectors.toSet());
		

			return new ArrayList<>(genreSet);

		}
		return getAllGenreResponse;
	}

	@Override
	public SongByGenreDetails getSongsByGenre(String genre, Integer limit, Integer offset) {

		String regexGenre = "(?i)\\b" + genre + "\\b";

		System.out.println("Genre: " + regexGenre);

		List<SongEntity> songDetailsList = songRepository.findByGenreRegex(regexGenre);

		SongByGenreDetails response = new SongByGenreDetails();

		List<SongDetails> songsList = new ArrayList<>();

		if (!songDetailsList.isEmpty()) {

			songsList = util.songDetailsMapper(songDetailsList);
			songsList = util.getSubList(songsList, offset, limit);
			response.setGenre(genre);
			response.setSongs(songsList);

		}
		return response;

	}

}
