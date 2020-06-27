package application;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedBitWriter {
  private byte currentByte;     // byte que esta sendo preenchido
  private byte numBitsWritten;  // numero de bits escritos no byte
  private BufferedOutputStream output; // saída do arquivo

  public BufferedBitWriter(String pathName) throws FileNotFoundException {
    currentByte = 0;
    numBitsWritten = 0;
    output = new BufferedOutputStream(new FileOutputStream(pathName));
  }

  public void writeBit(int bit) throws IOException {
    if (bit < 0 || bit > 1)
      throw new IllegalArgumentException("bit invalido: " + bit);
    
    numBitsWritten++;
    currentByte += bit << (8 - numBitsWritten);
    if (numBitsWritten == 8) {  // byte preenchido com os 8 bits
      output.write(currentByte);
      numBitsWritten = 0;
      currentByte = 0;
    }
  }

  public void close() throws IOException {
    output.write(currentByte);
    output.write(numBitsWritten);

    output.close();
  }
}