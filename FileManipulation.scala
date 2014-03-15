import java.io.File
import java.io._

package organizer {
package FileManipulation {
    object MoveFiles {
        
    
        def getCollectionDir() = {
            var f = new FileReader("organizer.conf")
            var b = new BufferedReader(f) 
            new File(b.readLine())
        }
    
    
        def moveFiles(f: TrackToIdentify, baseDir: File) {
            var dest = new File(baseDir, new File(f.suggestedMetadata.artist, f.suggestedMetadata.album).toString)
            println("Moving File to Collection Directory.\n")
                
            var format = if(f.pathToFile.toString.endsWith(".ogg")) {".ogg"}
            else if (f.pathToFile.toString.endsWith(".mp3")) {".mp3"}
            else {null}
                
            if (format != null) {
                var newFname = f.suggestedMetadata.artist + " - " +
                f.suggestedMetadata.trackNumber + " - " + f.suggestedMetadata.title + format
                
                if (dest.exists() == false) {
                    dest.mkdirs()
                }
        
                f.pathToFile.renameTo(new File(dest, newFname))
            }
        }
    }
}
}
