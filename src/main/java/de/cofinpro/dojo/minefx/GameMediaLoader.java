package de.cofinpro.dojo.minefx;


import javafx.scene.media.Media;

/**
 * Created by ABorger on 28.08.2015.
 */
public class GameMediaLoader {

    private static final String WIN_SOUND_FILENAME = "Bathroom_toilet_flushing.mp3";
    private static final String LOOSE_SOUND_FILENAME = "Poop.mp3";

    private Media winSound;
    private Media looseSound;

    public GameMediaLoader(){
        loadMediaInitalAsync();
    }

    private ClassLoader getClassloader(){
        return this.getClass().getClassLoader();
    }

    private void loadMediaInitalAsync(){
        Runnable task = () -> {
            getLooseSound();
            getWinSound();
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();
    }

    private Media getMediaForFilename(String filename){
        ClassLoader classLoader = getClassloader();
        String resourcePath = classLoader.getResource(filename).toString();
        return new Media(resourcePath);
    }

    public Media getWinSound() {
        if (winSound == null){
            winSound = getMediaForFilename(WIN_SOUND_FILENAME);
        }
        return winSound;
    }

    public Media getLooseSound() {
        if (looseSound == null) {
            looseSound = getMediaForFilename(LOOSE_SOUND_FILENAME);
        }

        return looseSound;
    }


}
