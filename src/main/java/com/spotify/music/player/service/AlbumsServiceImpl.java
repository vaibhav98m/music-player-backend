package com.spotify.music.player.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spotify.music.player.annotation.SkipLogging;
import com.spotify.music.player.entity.AlbumEntity;
import com.spotify.music.player.entity.SongEntity;
import com.spotify.music.player.model.AlbumModel;
import com.spotify.music.player.model.SongDetails;
import com.spotify.music.player.model.SongsInAlbumModel;
import com.spotify.music.player.repository.AlbumRepository;
import com.spotify.music.player.repository.SongRepository;
import com.spotify.music.player.utility.Util;

@Service
public class AlbumsServiceImpl implements AlbumsService {

	@Autowired
	SongRepository songRepository;

	@Autowired
	AlbumRepository albumRepository;

	@Autowired
	private Util util;

	@Override
	public SongsInAlbumModel getSongsByAlbumId(String albumId) {

		SongsInAlbumModel response = new SongsInAlbumModel();

//		updateIcon();

		Optional<List<AlbumEntity>> albumOptional = albumRepository.findByAlbumId(albumId);
		List<Integer> songIds = new ArrayList<>();
		if (!albumOptional.get().isEmpty()
				&& albumOptional.get().get(albumOptional.get().size() - 1).getAlbumId().equalsIgnoreCase(albumId)) {

			songIds = albumOptional.get().stream().map(AlbumEntity::getSongId).collect(Collectors.toList());
			List<SongEntity> songDetailsList = songRepository.findAllById(songIds);

			List<SongDetails> songs = util.songDetailsMapper(songDetailsList);
			response.setAlbumId(albumId);
			response.setAlbumName(albumOptional.get().get(0).getAlbumName());
			response.setSongs(songs);
			response.setImage(albumOptional.get().get(0).getImage());
		}

		return response;
	}

	@Override
	public List<AlbumModel> getAllAlbums(Integer limit, Integer offset) {

		List<AlbumModel> albums = new ArrayList<>();
		List<AlbumEntity> albumEntityList = albumRepository.findAll();

		// Filter by unique Id
		albums = util.getAllAlbumsMapper(albumEntityList).stream().filter(util.distinctByKey(AlbumModel::getAlbumId))
				.collect(Collectors.toList());

		albums = util.getSubList(albums, offset, limit);

		for (AlbumModel model : albums) {
			model.setAlbumFullName(model.getAlbumName());
			model.setAlbumName(util.regexAlbumName(model.getAlbumName()));
		}

		return albums;

	}

	@Transactional
	@SkipLogging
	private void updateIcon() {

		Integer count = 1;
		Integer anotherCount = 101;

		List<AlbumEntity> albumEntity = albumRepository.findAll();
		
		String albumName = albumEntity.get(0).getAlbumName();

		for (int j = 0; j < albumEntity.size(); j++) {
			
			if(albumEntity.get(j).getAlbumName().equalsIgnoreCase(albumName)) {
				albumEntity.get(j).setAlbumId("A" + anotherCount.toString());
			}else {
				albumName = albumEntity.get(j).getAlbumName();
				anotherCount++;
				albumEntity.get(j).setAlbumId("A" + anotherCount.toString());
			}

//			Optional<List<AlbumEntity>> albumOptional = albumRepository.findByAlbumId(count.toString());

//			String currentCOunt = albumOptional.get().get(0).getAlbumId();
//
//			for (int i = 0; i < albumOptional.get().size(); i++) {
//				System.out.println("=========" + "A" + anotherCount);
//				if (currentCOunt.equalsIgnoreCase(albumOptional.get().get(i).getAlbumId())) {
//					albumOptional.get().get(i).setAlbumId("A" + anotherCount);
//				}
//				else {
//					currentCOunt = albumOptional.get().get(i).getAlbumId();
//					
//				}
//			}
//			++anotherCount;
//			count++;

//		List<Integer> albumIds = albumsEntity.stream().map(AlbumEntity::getAlbumId).collect(Collectors.toList());

			albumRepository.saveAll(albumEntity);
		}

}

}
