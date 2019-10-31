package sysla;

public class TestGit {
  
  public void testbody() throws IOException {
		  
		  Response resp = RestAssured.get("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
		  
		 String data =resp.asString();
    
  }

}
