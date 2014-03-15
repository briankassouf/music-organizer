import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.AudioFileIO.read
import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.ogg.OggFileReader
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.tag
import org.jaudiotagger.tag._
import java.io.File
import java.net._
import java.io._
import xml._
import org.jaudiotagger.audio.ogg.util.OggPageHeader
import java.util.logging.LogManager
import java.util.logging._



package findrelease {

object GetAlbum {
    def main(args: Array[String]) {

        var dir = new File("/home/brian/Desktop/music/The Damnwells/Bastards of the Beat")

        var testFile = new File(dir.toString+ "/" + dir.list()(0))

        try {
            var log = AudioFileIO.logger
            var l = LogManager.getLogManager()
            log.setLevel(Level.OFF)
            var f = new FileHandler("/home/brian/logs.log")
            log.addHandler(f)
            l.addLogger(log)
//             println(/*AudioFileIO*/.defaultInstance)
//             Logger.getLogger("global")
            println(log.getHandlers())
            var file = AudioFileIO.read(testFile)
            var tag =file.getTag()
//             tag  = tag
            println(tag)
            var artist = tag.getArtist().get(0).toString.replace(" ", "+")
            println(artist)
            var numtracks = dir.listFiles().length
            println(numtracks)
            var year = tag.getYear().get(0).toString
            println(year)

            var title = getxml(artist, numtracks, year)
            println(title)
        } catch {
        case e:Exception => System.out.println("Crap! Something went wrong with the tags!", e)
        }
    }


    def getxml(Artist: String, numTracks: Int, year: String) = {
        var url = new URL("http://musicbrainz.org/ws/1/release/?type=xml&artist="+Artist+
        "&count="+numTracks+"&date="+year+"*");
//         println(url);
        try {
            var connection = url.openConnection()
            connection.setDoInput(true)

            var inStream = connection.getInputStream()

            var input = new BufferedReader(new InputStreamReader(inStream))
//	    println(input.readLine())
    
            var releaseList = (XML.loadString(input.readLine().toString) \ "release-list")(0)
            if (releaseList.attribute("count").toString == "Some(1)"){
                var release = (XML.loadString(releaseList.toString) \ "release")(0)
                var title = (XML.loadString(release.toString) \ "title")(0)

                title.text
            }
            else None
    //             
//             println(metadata.get("release"))
        } catch {
            case e:Exception => System.out.println("Crap! Something went wrong!", e)
        }
    }
}
}
// GetAlbum.main()
