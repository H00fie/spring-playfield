package bm.app.springplayfield;

import bm.app.springplayfield.models.Dwarf;
import bm.app.springplayfield.models.Elf;
import bm.app.springplayfield.models.Gnome;
import bm.app.springplayfield.models.Ogre;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class SpringPlayfieldApplication {

    public static void main(String[] args) {
        /**
         * Automatically creates a Spring Container for me (ApplicationContext)?
         */
        ApplicationContext context = SpringApplication.run(SpringPlayfieldApplication.class, args);

        Dwarf dwarf = context.getBean(Dwarf.class);
        dwarf.attackTheEnemy();
        printTheDivider();

       /**
        * Instead of ApplicationContext - an archaic solution with BeanFactory (requires spring.xml file within the
        * root directory, not the resources one.
        *
        *         BeanFactory factory = new XmlBeanFactory(new FileSystemResource("spring.xml"));
        *
        *         Elf elf = (Elf) factory.getBean("elf");
        *         elf.attackTheFoe();
        */

        /**
         * Spring Container solution with spring.xml file to configure the scope of beans.
         */
        ApplicationContext contextNew = new ClassPathXmlApplicationContext("spring.xml");
        Elf elf = (Elf) contextNew.getBean("elf");
        elf.setAge(678);
        System.out.println("I am an elf and my age is " + elf.getAge());
        printTheDivider();

        /**
         * Age variable is different than the default one because a previous object of Elf bean changed it and Elf is a singleton by default.
         */
        Elf anotherElf = (Elf) contextNew.getBean("elf");
        System.out.println("I am an elf and my age is " + anotherElf.getAge());
        printTheDivider();

        /**
         * An orc bean never being called by me.
         */

        /**
         * A gnome being of prototype scope.
         */
        Gnome gnome = (Gnome) contextNew.getBean("gnome");
        gnome.setAge(341);
        System.out.println(gnome.getAge());
        printTheDivider();

        /**
         * This bean's variable is the default one because the previous one changed it only for itself. Prototype scope allows for multiple objects.
         */
        Gnome anotherGnome = (Gnome) contextNew.getBean("gnome");
        System.out.println(anotherGnome.getAge());
        printTheDivider();

        /**
         * Ogre's default variable is set in spring.xml. By setting a default property there, I am making Spring Container call the setter.
         */

        Ogre ogre = (Ogre) contextNew.getBean("ogre");
        System.out.println("I am an ogre and my default age was set as a bean property in spring.xml file. It is: " + ogre.getAge());
        printTheDivider();
    }

    private static void printTheDivider() {
        System.out.println("-------------------------------");
    }
}
