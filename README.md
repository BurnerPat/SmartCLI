# SmartCLI

A simple utility to help your application working with those nasty command line arguments.

Command line arguments will be stored in a Java object using the Java Reflections API. While doing so,
SmartCLI will also keep track of the data types.

## Usage example

In order to use SmartCLI to parse command line arguments, you first have to create a so-called "Arguments Class".
This is a specially annotated class which will be used as the object to store your command line arguments in.

```java
@Arguments("test_app")
public class ParserArguments {
    
    // This is a switch argument. The user will use it as "-switch", without any value.
    @Switch
    // We would like to name the switch argument. If we do not do so, SmartCLI will use the field's name.
    @Name("switch")
    // Switch arguments always have to be boolean values.
    public boolean switchArg;

    // This argument is required and not optional.
    @Required
    @Name("string")
    // It's a simple String argument.
    public String stringArg;

    @Name("boolean")
    public boolean booleanArg;

    @Name("int")
    // For this argument, we provide a simple documentation.
    @Documentation("an integer value")
    public int intArg;

    @Name("float")
    public float floatArg;

    @Name("double")
    public double doubleArg;
    
    @Name("array")
    // This is an array argument. The user can specify the argument more than one time; every value will be stored
    // in the array. In this case, the argument can be passed between zero and three times.
    @Array(min = 0, max = 3)
    public String[] arrayArg;
    
    // This is not an argument, it will be ignored by SmartCLI.
    @Ignore
    public String notAnArgument;
    
    // This is the default argument. It captures every value without a preceding argument name.
    // For example, in "my_app -arg 3 my_file.txt" the value "my_file.txt" would be a default argument.
    @Default
    public String[] defaultArgs;
}
```

After declaring the arguments class, you can simply let SmartCLI do all the magic for you.

```java
public static void main(String[] args) {
    // Declare and initialize the SmartCLI object using defaults.
    SmartCLI<MyArguments> cli = new SmartCLI<MyArguments>(MyArguments.class);
    
    // Declare and initialize the arguments object.
    MyArguments obj = new MyArguments();

    try {
        // Parse the args array and put the values into our arguments object.
        cli.parse(args, obj);
    }
    catch (CLIException ex) {
        // Oh noes!
        // The API will throw exceptions if the args array contains wrongly formatted values or the access
        // via Java Reflections failed.
    }
}
```

It is really as simple as that! You can now read your argument values from the arguments object.

## Roadmap

This is what the future plans for this project currently look like:

- [ ] Release build (v 1.0.0) via GitHub
- [ ] Support for user defined argument types (v 1.1.0)

Of course, this project is open for your pull requests!

## License

The project is licensed under the Apache 2.0 license. See the `LICENCE` file for more information.