package com.spotify.music.player.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spotify.music.player.entity.SongEntity;

@Repository
public interface SongRepository extends MongoRepository<SongEntity, Integer> {

	public SongEntity findByTitle(String title);

	public List<SongEntity> findByYear(String year);

	public List<SongEntity> findByGenreRegex(String regex);
	
	
	public List<SongEntity> findByGenre(String regex);
	
	
	public List<SongEntity> findByTitleOrderByTitle(String title);
	
	

}
