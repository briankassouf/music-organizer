import java.io.File

package organizer {
  class TrackMetadata(ar: String, al: String, n: String, t: String) {
      var artist: String = ar
      var album: String = al
      var trackNumber: String = n
      var title: String = t
  }

  class TrackToIdentify(p: File, o: TrackMetadata, s: TrackMetadata) {
      var pathToFile: File = p
      var originalMetadata: TrackMetadata = o
      var suggestedMetadata: TrackMetadata = s
  }
}
