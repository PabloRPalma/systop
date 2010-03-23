package com.systop.common.security.jcaptcha.engine;

import java.awt.Color;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator
  .GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator
  .BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator
  .LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.systop.common.Constants;

/**
 * Captcha增强版本
 * 
 * @author david.turing@gmail.com
 * @modifyTime 21:01:52
 * @description 
 * <pre>
 *  安装 Captcha Instruction <br>
 *  1.add captchaValidationProcessingFilter 
 *    to applicationContext-acegi-security.xml<br>
 *  2.modify applicationContext-captcha-security.xml
 *    <ul>
 *    <li> make sure that captchaValidationProcessingFilter Call captchaService
      <li> config CaptchaEngine for captchaService (refer imageCaptchaService) 
      <li> write your own CaptchaEngine
      <li> config the following, so that We use CaptchaEngineEx to generate the 
          captcha image. 
      </ul>
          &lt;constructor-arg
 *              type="com.octo.captcha.engine.CaptchaEngine" index="1"&gt; 
 *              &lt;ref bean="captchaEngineEx"/gt; &lt;/constructor-arg&gt; 
 * </pre>
 */
public class CaptchaEngineEx extends ListImageCaptchaEngine {
  /**
   * ...
   */
  protected void buildInitialFactories() {
    
     //Set Captcha Word Length Limitation which should not over 6     
    Integer minAcceptedWordLength = new Integer(Constants.CAPTCHA_MIN_WORDS);
    Integer maxAcceptedWordLength = new Integer(Constants.CAPTCHA_MAX_WORDS);
    //Set up Captcha Image Size: Height and Width    
    Integer imageHeight = new Integer(Constants.CAPTCHA_IMG_HEIGHT);
    Integer imageWidth = new Integer(Constants.CAPTCHA_IMG_WIDTH);
    
    //Set Captcha Font Size    
    Integer minFontSize = new Integer(Constants.CAPTCHA_MIN_FONT_SIZE);
    Integer maxFontSize = new Integer(Constants.CAPTCHA_MAX_FONT_SIZE);
    //We just generate digit for captcha source char Although you can use
    //abcdefg......xyz
    WordGenerator wordGenerator 
      = new RandomWordGenerator(Constants.CAPTCHA_STRING);
 
     //cyt and unruledboy proved that backgroup not a factor of Security. A
     //captcha attacker won't affaid colorful backgroud, so we just use white
     //color, like google and hotmail.  
    BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(
        imageWidth, imageHeight, Color.white, Color.white);
  
     //font is not helpful for security but it really increase difficultness for
     //attacker     
    FontGenerator fontGenerator = new RandomFontGenerator(minFontSize,
        maxFontSize);    
     // Note that our captcha color is Blue     
    SingleColorGenerator scg = new SingleColorGenerator(Color.blue);
  
     //decorator is very useful pretend captcha attack. we use two line text
     //decorators.
     
    LineTextDecorator lineDecorator = new LineTextDecorator(1, Color.blue);
    // LineTextDecorator line_decorator2 = new LineTextDecorator(1, Color.blue);
    TextDecorator[] textdecorators = new TextDecorator[1];

    textdecorators[0] = lineDecorator;
    // textdecorators[1] = line_decorator2;

    TextPaster textPaster = new DecoratedRandomTextPaster(
        minAcceptedWordLength, maxAcceptedWordLength, scg,
        new TextDecorator[] { new BaffleTextDecorator(new Integer(1),
            Color.white) });

    //ok, generate the WordToImage Object for logon service to use.
    WordToImage wordToImage = new ComposedWordToImage(
        fontGenerator, backgroundGenerator, textPaster);
    addFactory(new GimpyFactory(wordGenerator, wordToImage));
  }

}
