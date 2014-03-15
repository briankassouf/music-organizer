import java.net.URLEncoder
import java.net._
import java.io._
import xml._


package organizer {
package MusicBrainz {
    object MusicBrainzQueries {

        def QueryMB(f: TrackToIdentify) {
    
            var originialArtist = URLEncoder.encode(f.originalMetadata.artist.replace(" ", "+")).replace("%2B", "+").toLowerCase().split("%")(0)
            var originialTitle = URLEncoder.encode(f.originalMetadata.title.replace(" ", "+")).replace("%2B", "+").toLowerCase().split("%")(0)
            var originialtracknum = URLEncoder.encode(f.originalMetadata.trackNumber).split("%")(0)
            var originialAlbum = URLEncoder.encode(f.originalMetadata.album.replace(" ", "+")).replace("%2B", "+").toLowerCase().split("%")(0)
    
            var url = new URL("http://musicbrainz.org/ws/1/track/?type=xml&artist="+
            originialArtist+"&title="+originialTitle+"&release="+
            originialAlbum+"&tracknumber="+(originialtracknum.toInt - 1));
            println(url)
            try {
                var connection = url.openConnection()
                connection.setDoInput(true)
    
                var inStream = connection.getInputStream()
    
                var input = new BufferedReader(new InputStreamReader(inStream))
    
                Thread.sleep(1000)
                var trackList = try {
                    (XML.loadString(input.readLine().toString) \ 
                    "track-list")(0)
    
                } catch {
                    case _ => null
                }
            
                if (trackList != null) {
                    var topTrack = trackList.child(0)
        
                    var suggestedTitle = (topTrack \ "title")(0).text
        
                    var suggestedArtist = ((topTrack \ "artist") \ "name")(0).text
        
                    var suggestedAlbum = (((topTrack \ "release-list") \ "release") \
                    "title")(0).text
        
                    var trackOffset = (((topTrack \ "release-list")(0) \ "release")(0) \
                    "track-list")(0).attribute("offset")
        
                    var suggestedTracknum = (trackOffset.get.toString.toInt + 1).toString
        
                    suggestedTracknum = if (suggestedTracknum.toInt < 10) {
                        ("0" + (suggestedTracknum.toString))
                    }
                    else { suggestedTracknum.toString}
        
        
                    println("Successfully retrieved track info from Music Brainz!\n")
        
                    var tag = new TrackMetadata(suggestedArtist, suggestedAlbum,
                    suggestedTracknum, suggestedTitle)
        
                    f.suggestedMetadata = tag
                    
                }
            } catch {
                case e:Exception => System.out.println("Crap! Something went wrong!", e)
            }
        }
    }
}
}
