import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.AudioFileIO.read
import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.mp3
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.audio.mp3._
import org.jaudiotagger.tag
import org.jaudiotagger.tag._
import java.io.File



package organizer {
package Tags {
    object ReadAndWriteTags {
    
        def GetFilesFromDir(dir: File) = {
            var listOfFileObjects: List[TrackToIdentify] = List()
            for(f <- dir.listFiles()){
		println("Reading file: " + f + "\n")
                var audiotag = if(f.toString.endsWith(".ogg")) {
                    var file = AudioFileIO.read(f)
                    file.getTag()
                }

                else if(f.toString.endsWith(".mp3")) {
// 		    println(file.hasID3v1Tag())
                    var reader = new MP3FileReader()
                    var file = reader.read(f)
                    file.getTag()
                }
                else if(f.toString.endsWith(".m4a")) {
                    var file = AudioFileIO.read(f)
                    file.getTag()
                }
                else { null }
                var trackToIdentify: TrackToIdentify = if (audiotag != null) {
                    var artist: String = audiotag.getFirstArtist().toString
                    var tracknum: String = audiotag.getFirstTrack().toString.split("/")(0)
                    var album: String = audiotag.getFirstAlbum().toString
                    var title: String = audiotag.getFirstTitle().toString
                    var t = new TrackMetadata(artist, album, tracknum, title)
                    println("done!")
                    new TrackToIdentify(f, t, null)
                }
                else {
                    println("Could not read file. Make sure it is a .mp3 or .ogg file.")
                    new TrackToIdentify(null, null, null)
                }
                listOfFileObjects = trackToIdentify :: listOfFileObjects
            }
            listOfFileObjects
        }

        def UpdateMetadata (f: TrackToIdentify) {
    
            var audioFile: AudioFile = null
            
            var tag = if (f.pathToFile.toString.endsWith(".ogg")) {
                audioFile = AudioFileIO.read(f.pathToFile)
                audioFile.getTag()
            }
            else if (f.pathToFile.toString.endsWith(".mp3")) {
                var reader = new MP3FileReader()
                audioFile = reader.read(f.pathToFile)
                audioFile.getTag()
            }
            else { null }
    
            if (f.originalMetadata.artist != f.suggestedMetadata.artist) {
                tag.setArtist(f.suggestedMetadata.artist)
                println("updated Artist to:" + f.suggestedMetadata.artist)
            }
    
            if (f.originalMetadata.album != f.suggestedMetadata.album) {
                tag.setAlbum(f.suggestedMetadata.album)
                println("updated Album to:" + f.suggestedMetadata.album)
            }
    
            if (f.originalMetadata.title != f.suggestedMetadata.title) {
                tag.setTitle(f.suggestedMetadata.title)
                println("updated Title to:" + f.suggestedMetadata.title)
            }
    
            if (f.originalMetadata.trackNumber != f.suggestedMetadata.trackNumber) {
                tag.setTrack(f.suggestedMetadata.trackNumber)
                println("updated TrackNumber to:" + f.suggestedMetadata.trackNumber)
            }
            if (f.pathToFile.toString.endsWith(".ogg")) {
                AudioFileIO.write(audioFile)
            }
            if(f.pathToFile.toString.endsWith(".mp3")) {
                var writer = new MP3FileWriter()
                writer.writeFile(audioFile)
            }
    
        }
    }
}
}
