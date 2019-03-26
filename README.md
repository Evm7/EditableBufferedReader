# EditableBufferedReader Project

## SAD  2018-2019 by Esteve Valls Mascaró and Hamza Errahmouni Barkam

### You can find on my [repository](https://github.com/Evm7/EditableBufferedReader) the next parts of the project:

- MVC EditableBufferedReader - editablebufferedreader_Line:
  * Console communication implemented using ProcessBuilder
  * Line modiffication using our own implemented class MyStringBuffer
  * Class EditableBufferedReader has Column Counter
  * The VIEW console implemented by using Observer/Observable
 
- MVC EditableBufferedReader - editablebufferedreader_EDITOR:
  * Same as the last MVC with its caractheristics but it has the possibility of editing a large line that has a bigger size than the columns of the terminal

### You can find on my partner's [repository](https://github.com/haerba/EditableBufferedReader) the next parts of the project:

- Sample EditableBufferedReader:
  * Console communication implemented using Runtime
  * Line modification uses a simple String for all the modifications
  * Class Line has Column Counter
  * The VIEW part is implemented on the EditableBufferedReader.readLine()

- MVC EditableBufferedReader:
  * Using pattern MVC
  * Line modification uses an array of Chars for all the modifications
  * Instead of Observer, it uses the class PropertyChangeSupport 

- Mouse implementation on the MVC pattern
  * We implement some movements of the functionality of the mouse.
  

