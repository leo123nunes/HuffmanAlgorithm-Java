package application;
import java.io.*;

public class BufferedBitReader {
  
  int current;    
  int next;       
  int afterNext;  
  int bitMask;    
  
  BufferedInputStream input;

  public BufferedBitReader(String pathName) throws IOException {
    input = new BufferedInputStream(new FileInputStream(pathName));
    
    current = input.read();
    if (current == -1)
      throw new EOFException("O arquivo não possui bytes suficiente");
    
    next = input.read();
    if (next == -1) 
      throw new EOFException("O arquivo não possui bytes suficiente");  
    
    afterNext = input.read();
    bitMask = 128;   
  }

  public int readBit() throws IOException {
    int returnBit;   
    
    if (afterNext == -1) {  // Verifica se está no último bit
      
      // quanfo for == -1, o next vai receber a quantidade de bits restantes
      
      if (next == 0)
        return -1;    // Sem bits restantes para retornar
      else {
        if ((bitMask & current) == 0)
          returnBit = 0;
        else 
          returnBit = 1;
        
        next--;
        bitMask = bitMask >> 1; 
        return returnBit;
      }
    }
    else {
      if ((bitMask & current) == 0)
        returnBit = 0;
      else 
        returnBit = 1;
      
      bitMask = bitMask >> 1;
      
      if (bitMask == 0)  {        // Terminou de retornar o byte
        bitMask = 128; 
        current = next;
        next = afterNext;
        afterNext = input.read();
      }
      return returnBit;
    }
  }

  public void close() throws IOException {
    input.close();
  }
  
}