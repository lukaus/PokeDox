# PokeDox
A program to keep track of your Pokemon collection! Primarily to help me complete the national dex

By Luke Stanley

Version numbering is more of just milestones, can't even remember most of them, but here ya go:

VERSION 0.7 INCLUDES:

    -   Finished (visually) GUI
    -   Completed Pokemon list with dex numbers/types

'Blockers' for 0.8:

    -   Filter for types and collection
    -   Region filter occasionally fails with ArrayIndexOutOfBounds exceptions: probably a race condition somewhere (may be in button implementation)

TODO for final version (0.9 - 1.0):

    -   Implement the rest of the GUI
    -   Implement persistence of data, either through serialization, or, more likely, writing and reading from pokemon.dat (eventually perhaps allow user-defined 'saves')
    -   Make pokemon's icon appear in the proper panel when selected
    -   Implement sorting/viewing by evolution family