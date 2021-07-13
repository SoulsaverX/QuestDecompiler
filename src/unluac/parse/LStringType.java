package unluac.parse;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public abstract class LStringType extends BObjectType<LString> {

  public static LStringType50 getType50() {
    return new LStringType50();
  }
  
  public static LStringType53 getType53() {
    return new LStringType53();
  }
  
  protected ThreadLocal<StringBuilder> b = new ThreadLocal<StringBuilder>() {
    
    @Override
    protected StringBuilder initialValue() {
      return new StringBuilder();  
    }

  };
  
}

class LStringType50 extends LStringType {
  @Override
  public LString parse(final ByteBuffer buffer, BHeader header) {
    BSizeT sizeT = header.sizeT.parse(buffer, header);
    final StringBuilder b = this.b.get();
    b.setLength(0);
    final int endl = sizeT.asInt();
    byte[] test = new byte[sizeT.asInt()];
    sizeT.iterate(new Runnable() {
      int i = 1;
      int i2 = 0;
      @Override
      public void run() {
    	  if(header.Encoded) {
    		  if(i == endl)i=0;
    		  test[i2] = (byte) ( buffer.get()-i);
    		  b.append((char) (test[i2] -i));
    		  i++;
    		  i2++;
    	  }else
    		  b.append((char) (0xFF & buffer.get()));
      }
      
    });
    
    //String s = b.toString();
    String s = new String(test,Charset.forName("GBK"));
    if(header.debug) {
      System.out.println("-- parsed <string> \"" + s + "\"");
    }
    return new LString(sizeT, s);
  }
}

class LStringType53 extends LStringType {
  @Override
  public LString parse(final ByteBuffer buffer, BHeader header) {
    BSizeT sizeT;
    int size = 0xFF & buffer.get();
    if(size == 0xFF) {
      sizeT = header.sizeT.parse(buffer, header);
    } else {
      sizeT = new BSizeT(size);
    }
    final StringBuilder b = this.b.get();
    b.setLength(0);
    sizeT.iterate(new Runnable() {
      
      boolean first = true;
      
      @Override
      public void run() {
        if(!first) {
          b.append((char) (0xFF & buffer.get()));
        } else {
          first = false;
        }
      }
      
    });
    b.append('\0');
    String s = b.toString();
    if(header.debug) {
      System.out.println("-- parsed <string> \"" + s + "\"");
    }
    return new LString(sizeT, s);
  }
}
