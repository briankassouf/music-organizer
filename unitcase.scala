

object GetUnitCase {
    def unitcase() {
        val files = 
            <files>
                <file>
                    <artist>Ben Folds Five</artist>
                    <album>Whatever and Ever Amen</album>
                    <title>One Angry Dwarf and 200 Solemn Faces</title>
                    <year>2005-03-22</year>
                </file>
                /*<file>
                    <artist>Jimmy Eat World</artist>
                    <album>Chase This Light</album>
                    <title>Firefight</title>
                    <year></year>
                </file>*/
            </files>;

//         return(files)
        println(((files \\ "files") \\ "file")(0))
    }
}
//GetUnitCase.unitcase()
