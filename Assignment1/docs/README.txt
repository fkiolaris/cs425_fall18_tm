Server:
javac -cp libs/jackson-all-1.9.0.jar src/reverse_server_multi_threaded/*.java 
java -cp .:libs/jackson-all-1.9.0.jar src.reverse_server_multi_threaded.ReverseServer 9999

Client:
javac -cp libs/jackson-all-1.9.0.jar src/reverse_server_multi_threaded/*.java 
java -cp .:libs/jackson-all-1.9.0.jar src.reverse_server_multi_threaded.ReverseClient [IP] [PORT] [REPETITIONS]
