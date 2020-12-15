package SessionManager;

/**
 * Created by Faisal Waris on 3/27/2018.
 */

public class Config {
    //old http://akkastechdemo.website/bms/public/api/


  //  public static final String BASE_URL="http://192.168.0.60/bms/public/api/";
    //live : app.mysoccertraining.com
    //local: demo.mysoccertraining.com
 public static final String BASE_URL="https://axetv.net/api/v2/";

 // public static final String BASE_URL="http://mysoccertraining.com/api/";



    //login
   public static final String SIGN_IN=BASE_URL+"login";

    public static final String LOGOUT_FROM_ALLDEVICES=BASE_URL+"logout/from/others";
    public static final String LOGOUT=BASE_URL+"user/logout";

    public static final String FORGOT_PASSWORD=BASE_URL+"password/email";
    public static final String FACEBOOK_LOGIN=BASE_URL+"fb/login";


    //Notes
    public static final String GOALS=BASE_URL+"note";
    public static final String ADD_NOTES=BASE_URL+"note/add";
    public static final String EDIT_NOETS=BASE_URL+"note/update";
    public static final String NOTE_SHARE=BASE_URL+"note/share";
    public static final String DELETE_NOTE=BASE_URL+"note/delete";

  //LeaderBoard
    public static final String LEADER_BOARD=BASE_URL+"user/leaderboard";


    //Add Team Training
  public static final String ADD_TEAM_TRAINING="team-training/add/date";

    //LeaderBoard monthly
    public static final String LEADER_BOARD_MONTHLY=BASE_URL+"user/leaderboard/monthly";

    //stats
    public static final String STATS=BASE_URL+"user/stats";
    public static final String TROPHIES=BASE_URL+"user/trophies";

    //dummy time
    public static final String GET_TIME_ZONE=BASE_URL+"get/time_zone";

    //Pending Assessment
    public static final String PENDING_ASSESSMENT=BASE_URL+"bmsu/pending";
    public static final String PENDING_ASSESSMENT_QUIZ=BASE_URL+"drill/quiz/";
    public static final String PENDING_ASSESSMENT_QUIZ_FINISHED=BASE_URL+"drill/quiz-attempt";

    //today training Agenda
    public static final String TODAY_TRAINING_AGENDA=BASE_URL+"sessions/today";
    public static final String TODAY_TRAINING_AGENDA_DELETE=BASE_URL+"today/session/delete";
    public static final String DELETET_TEAM_TRAINING=BASE_URL+"delete/training/";

    public static final String Today_TRAINING_AGENDA_SESSION_DETAIL=BASE_URL+"session/";
    public static final String MAIN_IMAGES_TODAY_TRAINING_AGENDA="https://app.mysoccertraining.com/images/sessions/";
// public static final String MAIN_IMAGES_TODAY_TRAINING_AGENDA="http://mysoccertraining.com/images/sessions/";
    //https://app.mysoccertraining.com/api/



    public static final String IMAGE_USER="http://192.168.0.60/bms/public/images/";


    //time zone update Api
    public static final String UPDATE_TIME_ZONE=BASE_URL+"update/timezone";






    //Seen Api

    public static final String SEENAPI_AFTERSEENVIDEO=BASE_URL+"drills/";
    //my training
    public static final String MY_TRAINING=BASE_URL+"my-training";
    public static final String ADD_SESSION=BASE_URL+"add/session";

    public static final String GET_TEAM_TRAINING_DATES=BASE_URL+"get/team/trainings";


    //BMSU FRAGMENT
    public static final String BMSU_VIDEO=BASE_URL+"user/bmsu";


    //SESSION FRAGMENT
    public  static final String SESSION_LIST=BASE_URL+"sessions?page=";


    //COUNT SIDE_BAR
    public static final String COUNT_SIDE_BAR=BASE_URL+"user/counts";

    //to get cancel subscription information
    public static final String GET_CANCEL_SUBSCRIB_INFO = BASE_URL + "";

    //Profile
    public static final String PROFILE_DATA=BASE_URL+"user/profile/data";

    //Like Profile
    public static final String LIKE_PROFILE=BASE_URL+"like";

    //Comment Profile
    public static final String COMMENT_PROFILE=BASE_URL+"comment";


    //Upload Post with image and text
    public static final String UPLOAD_POST=BASE_URL+"user/create/post";

    //Change User Image
    public static final String CHANGE_IMAGE=BASE_URL+"image/upload";

    //News Feed
    public static final String NEWS_FEED=BASE_URL+"news/feed";

    //User Gallery
    public static final String USER_GALLERY=BASE_URL+"user/gallery";

    //unRead Conversation Count
    public static final String UNREAD_CONVERSATION_Count=BASE_URL+"unread/conversation";

    //Read the Unread Conversation Seen
    public static final String READ_UNREAD_CONVERSATION_SEEN=BASE_URL+"conversation/seen/";


    //Open Chat of User
    public static final String OPEN_CHAT_USER=BASE_URL+"get/conversation?page=";

    //Message Send
    public static final String MESSAGE_SEND=BASE_URL+"send/message";

    //Get Friends
    public static final String GET_FRIENDS=BASE_URL+"user/friends";

    //Accept Request
    public static final String ACCEPT_REQUEST=BASE_URL+"user/accept/request";

    //Reject Friend Request
    public static final String REJECT_FRIEND_REQUEST=BASE_URL+"user/reject/request/";

    //Cancel Request
    public static  final String CANCEL_REQUEST=BASE_URL+"user/cancel/request";

    //REMOVE FRIENDS
    public static final String REMOVE_FRIENDS=BASE_URL+"user/remove/friend";

    //ELASTIC SEARCH
    public static final String SEARCH=BASE_URL+"search?q=";

    //Add Friends
    public static final String ADD_FRIENDS=BASE_URL+"user/add/friend";


    //get Friends Stats
    public static final String FRIENDS_STATS=BASE_URL+"friend/stats/";

    //get Friends Trophies
    public static final String FRIENDS_TROPHIES=BASE_URL+"friend/trophies/";


    // Friends Post
    public static final String FRIENDS_POST=BASE_URL+"friend/profile/data?id=";

    //Friends Gallery
    public static final String FRIENDS_GALLERY=BASE_URL+"friend/gallery?id=";



    //Delete Image Gallery
    public static final String DELETE_IMAGE_GALLERY=BASE_URL+"post/delete";
    //Friends of Friends
    public static final String FRIENDS_OF_FRIENDS=BASE_URL+"friend/of/friends?user_id=";

    //Strip api
    public static final String STRIPE_API=BASE_URL+"payment";



    // preferences login
     public static final String PREF_LOGIN="PREF_USER_LOGIN";
     public static final String PREF_USER_TOKEN="PREF_USER_TOKEN";

}
