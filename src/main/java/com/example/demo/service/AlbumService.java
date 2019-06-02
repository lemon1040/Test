package com.example.demo.service;

import com.example.demo.entity.Album;
import com.example.demo.entity.Song;

import java.util.ArrayList;

public interface AlbumService {
    ArrayList<Album> getCommandAlbum(String userid);
    Album getAlbumByAlbumId(String albumid);
    ArrayList<Album> getAlbumByNamePart(String name);
    ArrayList<Song> getSongsInAlbum(String albumid);
}
