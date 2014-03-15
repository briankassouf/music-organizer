import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.AudioFileIO.read
import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.mp3
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.audio.mp3._
import org.jaudiotagger.tag
import org.jaudiotagger.tag._
import java.io.File
import java.net.URLEncoder
import java.net._
import java.io._
import xml._
import java.lang.System
import java.util.logging.LogManager
import java.util.logging._
import organizer.MusicBrainz._
import organizer.Tags._
import organizer.FileManipulation._


package organizer {

  object Main {
  
    def main(args: Array[String]) {
        setupLogging()
        var dir = if (getSetting("folder_to_use") == "") {
          new File(System.getProperty("user.home"), "Desktop/Tracks To Identify")
        } else {
          new File(getSetting("folder_to_use"))
        }
        if (dir.exists() == false) {
          dir.mkdir()
          println("Made Directory 'Tracks To Identify' on Desktop. Put music in there and run program again.")
        }
        if (dir.listFiles().length > 0) {
            var fileObjects = Tags.ReadAndWriteTags.GetFilesFromDir(dir)
            for (f <- fileObjects) {
                println(f.pathToFile)
                if (f.pathToFile != null) {
                    MusicBrianz.MusicBrainzQueries.QueryMB(f)
                    if (f.suggestedMetadata != null) {
                        Tags.ReadAndWriteTags.UpdateMetadata(f)
                        var collectionDir = new File(getSetting("collectionDir"))
                        FileManipulation.MoveFiles.moveFiles(f, collectionDir)
                    }
                }
            }
        }
        daemonize()
    }
    
    
    def setupLogging() {
      var logs: List[java.util.logging.Logger] = List()
      logs = AudioFileIO.logger :: logs
      logs = vorbiscomment.VorbisCommentReader.logger :: logs
      logs = id3.AbstractID3Tag.logger :: logs
      logs = datatype.AbstractDataType.logger :: logs
      for (l <- logs) {
        l.setLevel(Level.OFF)
        var f = new FileHandler("logs/logs.log")
        l.addHandler(f)
      }
    }
    
    def getSetting(target: String) = {
      var f = new FileReader("organizer.conf")
      var b = new BufferedReader(f)
      var cursor = b.readLine()
      while (!cursor.startsWith(target) && f.ready()) {
        cursor = b.readLine()
      }
      if (cursor != "" && cursor.split("=")(0) == target) {
        var value = cursor.split("=")(1)
        value
      } else {
        ""
      }
    }
    
    def daemonize() {
      Thread.sleep(1000*60*2)
      main(Array("run"));
    }
  }
}
