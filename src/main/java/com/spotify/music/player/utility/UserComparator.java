package com.spotify.music.player.utility;

import com.spotify.music.player.entity.UserEntity;
import com.spotify.music.player.model.ClerkUserModel;

public class UserComparator {

	public static boolean compareUserModelAndEntity(ClerkUserModel userModel, UserEntity userEntity) {
		if (userModel == null || userEntity == null) {
			return false;
		}
		
		boolean idMatch = true;
		boolean emailMatch = true;
		boolean userNameMatch = true;

		if (!userModel.getId().equalsIgnoreCase(userEntity.getId())) {
			idMatch= false;
		}

		if (!userModel.getUsername().equalsIgnoreCase(userEntity.getUsername())) {
			userNameMatch= false;
		}

		for (int i = 0; i < userModel.getEmailAddresses().size(); i++) {
			if (!userModel.getEmailAddresses().get(i).getEmailAddress().equalsIgnoreCase(userEntity.getEmail())) {
				emailMatch= false;
			}
		}

		return idMatch && emailMatch && userNameMatch;
	}
}