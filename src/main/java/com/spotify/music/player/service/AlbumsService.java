package com.spotify.music.player.service;

import java.util.List;

import com.spotify.music.player.model.AlbumModel;
import com.spotify.music.player.model.SongsInAlbumModel;

public interface AlbumsService {

	SongsInAlbumModel  getSongsByAlbumId(String id);

	List<AlbumModel> getAllAlbums(Integer limit, Integer offset);

}
