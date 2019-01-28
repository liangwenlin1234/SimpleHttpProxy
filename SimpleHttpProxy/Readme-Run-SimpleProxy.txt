The SimpleProxy program is built with Gradle 4.6 and Java 8

Open a console, go to the the project root directory and run gradle to kick off the 
SimpleProxy on local port 9999:

    gradle -PmainClass=simplehttpproxy.SimpleProxy execute
  
Open another console, try some urls like:
    
    curl -x http://localhost:9999/proxy http://www.google.com

    curl -x http://localhost:9999 http://httpbin.org/

    curl -x http://localhost:9999 http://httpbin.org/get

    curl -x http://localhost:9999 "http://httpbin.org/get?userId=101&name=tester"
    
    curl -x http://localhost:9999 -X POST http://httpbin.org/post

    curl -x http://localhost:9999 -X POST -d "asdf=blah&sdfg=klmj" http://httpbin.org/post

For testing:
    gradle clean test
    
limitation: 
    HTTP redirects are not be handled. 
    embedded urls like <img src="/images/smiley.gif"> are not handled.
    
