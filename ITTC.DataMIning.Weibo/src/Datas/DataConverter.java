package Datas;

import weibo4j.Trend;
import weibo4j.model.Comment;
import weibo4j.model.Status;
import weibo4j.model.User;

public final class DataConverter {
	// public static EmotionData ConvertFrom(EmotionInfo emotionInfo)
	// {
	// var data = new EmotionData();
	// data.Category = emotionInfo.Category;
	// data.IsCommon = emotionInfo.IsCommon;
	// data.IsHot = emotionInfo.IsHot;
	// data.OrderNumber = emotionInfo.OrderNumber;
	// data.Phrase = emotionInfo.Phrase;
	// data.Type = emotionInfo.Type;
	// data.Url = emotionInfo.Url;

	// return data;
	// }
	// public static PeriodTrendData ConvertFrom(PeriodTrendInfo
	// periodTrendInfo)
	// {
	// var data = new PeriodTrendData();
	// data.Name = periodTrendInfo.Name;
	// data.Query = periodTrendInfo.Query;

	// return data;
	// }

	public static CommentData ConvertFrom(Comment commentInfo) {
		CommentData data = new CommentData();
		data.setCreatedAt(commentInfo.getCreatedAt());
		data.setID(commentInfo.getId());
		data.setSource(commentInfo.getSource());
		data.setStatus(ConvertFrom(commentInfo.getStatus()));
		data.setText(commentInfo.getText());
		data.setUser(ConvertFrom(commentInfo.getUser()));

		return data;
	}

	// public static TrendData ConvertFrom(TrendInfo trendInfo)
	// {
	// var data = new TrendData();
	// data.Hits = trendInfo.Hits;
	// data.HotWord = trendInfo.HotWord;
	// data.ID = trendInfo.ID;
	// return data;
	// }

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Public Methods

	public static StatusData ConvertFrom(Status statusInfo) {
		StatusData data = new StatusData();

		try {

			data.setID(Long.parseLong(statusInfo.getId()));
			data.setText(statusInfo.getText());
			// data.setSource(statusInfo.getSource());

			// data.setReplyTo(statusInfo.getRetweetedStatus());

			// data.setReplyToUserId(statusInfo.InReplyToStuatusID);
			// data.setReplyToUserScreenName(statusInfo.InReplyToScreenName);
			// data.setThumbnailPic(statusInfo.ThumbnailPictureUrl);
			;
			data.setMiddlePic(statusInfo.getThumbnailPic());
			data.setOriginalPic(statusInfo.getOriginalPic());

			data.setCreatedAtTime(statusInfo.getCreatedAt());

		}

		catch (RuntimeException ex) {
			data.setCreatedAtTime(new java.util.Date());

		}

		data.setreposts_count(statusInfo.getRepostsCount());
		data.setcomments_count(statusInfo.getCommentsCount());

		if (statusInfo.getRetweetedStatus() != null) {
			data.setRetweetedStatusText(statusInfo.getRetweetedStatus()
					.getText());
		}
		if (null != statusInfo.getUser()) {
			data.setUserData(ConvertFrom(statusInfo.getUser()));
		}

		if (null != statusInfo.getRetweetedStatus()) {
			data.setRetweetedStatus(ConvertFrom(statusInfo.getRetweetedStatus()));
		}

		return data;
	}

	public static UserData ConvertFrom(User userInfo) {

		UserData tempVar = new UserData();
		tempVar.setID(Long.parseLong(userInfo.getId()));
 
		tempVar.setScreenName(userInfo.getScreenName());
		tempVar.setName(userInfo.getName());
		// tempVar.setProvince(userInfo.getProvince());
		// tempVar.setCity(userInfo.getCity());
		tempVar.setLocation(userInfo.getLocation());
		tempVar.setDescription(userInfo.getDescription());
		tempVar.setUrl(userInfo.getUrl());
		tempVar.setProfileImageUrl(userInfo.getProfileImageUrl());

		tempVar.setGender(userInfo.getGender());
		tempVar.setFollowersCount(userInfo.getFollowersCount());
		tempVar.setFriendsCount(userInfo.getFriendsCount());
		tempVar.setStatusesCount(userInfo.getStatusesCount());

		UserData data = tempVar;
		data.setCreatedAt(userInfo.getCreatedAt());
		data.setFavouritesCount(userInfo.getFavouritesCount());

		// if (null != userInfo.Status)
		// {
		// data.LatestStatus = ConvertFrom(userInfo.Status);
		// }

		return data;
	}

}