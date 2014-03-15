import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.AudioFileIO.read
import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.tag
import org.jaudiotagger.tag._
import java.io.File

object Main {
  def main() {



    audioFile = AudioFileIO.read("/home/brian/Desktop/01 - Bag It Up.ogg");
    audioFile.getTag();

  }
}
