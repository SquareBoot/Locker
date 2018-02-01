## Windows Batch scripts

#### Client
Copy <code>Client.bat</code> in the same folder of <code>Client.jar</code> and run the script instead of the JAR. The script will check if <code>explorer.exe</code> has been started after Locker exited. If it isn't running, the Batch script will restart it, restoring the Windows Start Menu and Desktop.
<br>Please note that <code>explorer.exe</code> and the Windows task manager are closed to avoid that a user kills or closes Locker.

#### Server
Like the client script, copy it in the JAR folder. It does not affect Explorer and it is useless. However, its aim is to get a fast executable file to start when the server computer boots.