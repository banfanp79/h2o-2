package hex.deeplearning;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class DropoutTest {

  @Test
  public void test() throws Exception {
    final int units = 1000;

    Neurons.DenseVector a = new Neurons.DenseVector(units);
    double sum1=0, sum2=0, sum3=0, sum4=0;

    final int loops = 10000;
    for (int l = 0; l < loops; ++l) {
      long seed = new Random().nextLong();

      Dropout d = new Dropout(units, 0.3);
      Arrays.fill(a.raw(), 1f);
      d.randomlySparsifyActivation(a, seed);
      sum1 += water.util.Utils.sum(a.raw());

      d = new Dropout(units, 0.0);
      Arrays.fill(a.raw(), 1f);
      d.randomlySparsifyActivation(a, seed + 1);
      sum2 += water.util.Utils.sum(a.raw());

      d = new Dropout(units, 1.0);
      Arrays.fill(a.raw(), 1f);
      d.randomlySparsifyActivation(a, seed + 2);
      sum3 += water.util.Utils.sum(a.raw());

      d = new Dropout(units, 0.314);
      d.fillBytes(seed+3);
//      Log.info("loop: " + l + " sum4: " + sum4);
      for (int i=0; i<units; ++i) {
        if (d.unit_active(i)) {
          sum4++;
          assert(d.unit_active(i));
        }
        else assert(!d.unit_active(i));
      }
//      Log.info(d.toString());
    }
    sum1 /= loops;
    sum2 /= loops;
    sum3 /= loops;
    sum4 /= loops;
    Assert.assertTrue(Math.abs(sum1-700)<1);
    Assert.assertTrue(sum2 == units);
    Assert.assertTrue(sum3 == 0);
    Assert.assertTrue(Math.abs(sum4-686)<1);
  }

}
