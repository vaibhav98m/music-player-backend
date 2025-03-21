package com.spotify.music.player.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.spotify.music.player.annotation.SkipLogging;
import com.spotify.music.player.entity.AlbumEntity;

@Repository
public interface AlbumRepository extends MongoRepository<AlbumEntity, Integer> {

	@SkipLogging
	public Optional<List<AlbumEntity>> findByAlbumId(@NonNull String id);

	public AlbumEntity findByAlbumName(String title);

	public void save(String writeValueAsString);
	
	

}
