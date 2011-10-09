# Introduction

## What is it?

xStructure is a plugin for [IntelliJ IDEA][http://www.jetbrains.com/idea/] Java IDE.

It provides a flexible way of displaying structure of XML files, not only based on tags names, but using attributes and tag values to make navigation easier. 

**Example:** 

Instead of this:

![Default view](https://github.com/syllant/idea-plugin-xstructure/raw/master/src/main/doc/screenshots/wiki_default_view_webapp.png)

you could see:

<table>
  <tr>
    <th>this:</th>
    <td>![xStructure sample 1](https://github.com/syllant/idea-plugin-xstructure/raw/master/src/main/doc/screenshots/wiki_xs_view_webapp2.png)</td>
  </tr>
  <tr>
    <th>or this:</th>
    <td>![xStructure sample 2](https://github.com/syllant/idea-plugin-xstructure/raw/master/src/main/doc/screenshots/wiki_xs_view_webapp1_v2.png)</td>
  </tr>
</table>

## How do I install it?

IntelliJ IDEA provides a plugin manager which allows user to download plugins from IDE. Plugin manager is available from Settings dialog. No further step is required, see [Settings][8] for available options. 

## How does it work?

xStructure provides a flexible way to customize Structure view. This is achieved through custom mapping definition files. These files define how each XML element must be displayed (or hidden) inside Structure view. Rules are based on regular expressions or XPath. A mapping definition file is associated to one or more DTD or XML Schemas. When a XML file is opened inside IntelliJ IDEA, Structure view is automatically customized according to mappings found for current file. 

## How do I find mapping definition files?

A set of definition files are bundled in xStructure plugin. They target some common XML file types: Maven, Web Application, Portlet Application, Spring Beans, Hibernate Mapping,... These files are automatically copied in %INTELLIJ\_CONFIG\_DIR%/xstructure/default directory so you may customized them. 

You'll also find other definition files on this page: [Mapping Definition Repository](https://github.com/syllant/idea-plugin-xstructure/wiki/MappingDefinitionRepository).

## Can I create or change mapping definition files?

Yes, xStructure plugin loads all XML files stored in %CONFIG_DIR%/xstructure directory, recursively. You can change existing files or create new ones. See this page to help you writing mapping definition files (but reading an existing file should be enough): [CustomizationGuide][13]. 

You are welcome to share your contributions by sending them to me on *syllant:gmail*. I will publish them on [mapping definition repository](https://github.com/syllant/idea-plugin-xstructure/wiki/MappingDefinitionRepository).

## What is planned for next releases?

See the [Features][https://github.com/syllant/idea-plugin-xstructure/wiki/Features] page to see features planned for next releases.

You are also welcome to send your requests using [Issue tracker](https://github.com/syllant/idea-plugin-xstructure/issues).

## Found a bug?

Please, describe it using [Issue tracker](https://github.com/syllant/idea-plugin-xstructure/issues).

## Credits

Thanks to YourKit for providing a free licence for [YourKit Java Profiler](http://www.yourkit.com/java/profiler/index.jsp).

*YourKit is kindly supporting open source projects with its full-featured Java Profiler.*
*YourKit, LLC is the creator of innovative and intelligent tools for profiling Java and .NET applications.*
*Take a look at !YourKit's leading software products: [YourKit Java Profiler](http://www.yourkit.com/java/profiler/index.jsp) and [YourKit .NET Profiler](http://www.yourkit.com/.net/profiler/index.jsp).*