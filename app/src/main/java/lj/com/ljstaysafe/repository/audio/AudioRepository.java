package lj.com.ljstaysafe.repository.audio;

public interface AudioRepository {
    void saveRingerMode(int ringerMode);
    int getSavedRingerMode();
}
