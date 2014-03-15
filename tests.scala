import scala.testing.SUnit
import SUnit._
//import GetUnitCase.unitcase
import findrelease._

package Tests {

object Test {
  
    def main(args: Array[String]) {
        val testFiles = getUnitCase()
//         println(testFiles)
        class MyTest(n:String) extends TestCase(n) {
            override def runTest() = n match {
                case "testFindAlbum" => {
                    for(val f <- ((testFiles \\ "files") \\ "file")) {
                        val artist = (f \\ "artist")(0).text.replace(" ", "+")
                        val totalTracks = (f \\ "totalTracks")(0).text.toInt
                        val year = (f \\ "year")(0).text
                        val album = (f \\ "album")(0).text
                        val foundAlbum = findrelease.GetAlbum.getxml(artist, totalTracks, year)
                        assertEquals(foundAlbum, album)
                    }
                }
            }
        }
        val suite = new TestSuite( new MyTest("testFindAlbum")) 
//         suite.addTest( new MyTest("myTest2"))
    
        val r = new TestResult(); 
        suite.run(r)
    
        for(val tf <- r.failures()) { 
            println( tf.toString()) 
        }

    
    }

    def getUnitCase() = {
        val files = 
            <files>
                <file>
                    <artist>Ben Folds Five</artist>
                    <album>Whatever and Ever Amen</album>
                    <title>One Angry Dwarf and 200 Solemn Faces</title>
                    <year>2005-03-22</year>
                    <totalTracks>19</totalTracks>
                </file>
            </files>;

        files
    }
}}

//  more like this:
// (03:58:35 PM) chris easyweaze: yeah - filenames, and potentially other info in the future...
// (03:59:22 PM) chris easyweaze: so, something like this:
//   <directory>
//     <file>
//       <filename>01. track.mp3</filename>
//       <tags>
//         <artist>blab</artist>
// or
// <testCase>
//   <expectedResult>
//     <artist>Ben Folds...</artist>
//     <album> ... </album>
//     ...
//   </expectedResult>
//   <directory>
//     ...
//   </directory>
// </testCase>/
// Test.main()
