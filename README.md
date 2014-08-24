# SpringMVC-UADetectors
---------------------

Simple library that can be used to wrap awesome <a href="https://github.com/before/uadetector">uadetector</a>
library into your springmvc project.

## Java Configuration

Just add `EnableUADetector` to be able to inject `ReadableUserAgent` arguments to your controller methods.
This annotation can be configured with following parameters:


| Parameter  | Values    | Description                                                                              |
|------------|-----------|------------------------------------------------------------------------------------------|
| `cache`    | `NONE`    | Do not use any cache. This is the default.                                               |
|            | `DEFAULT` | Use simple cache provided by springmvc-uadetector (use a concurrent hashmap internally). |
|            | `GUAVA`   | Use guava cache (guava  must be on classpath).                                           |
|            | `AUTO`    | Use guava cache if guava is available or switch to simple cache implementation.          |
| `autowire` | `true`    | Allow ReadableUserAgent and Browser arguments to be autowired.                           |
|            | `false`   | Disable autowiring of ReadableUserAgent and Browser arguments. This is the default.      |

## Additions

In addition to `ReadableUserAgent` object, springmvc-uadetector allow you to use
a `Browser` object that contains shortcuts on top of `ReadableUserAgent` to easily
check well known browsers and specific Internet Explorer versions.

Here is a sample:

```java
Browser browser = new Browser(readableUserAgent);

browser.isIE();
browser.isFirefox();
browser.isOpera();
browser.isSafari();
browser.isGoogleChrome();
browser.isChromium();

browser.isIE6();
browser.isIE7();
// And more...
```

## Sample

Here is a standard spring configuration:

```java
package com.myApp;

import org.springframework.web.servlet.config.annotation.EnableWebMvc
import com.github.mjeanroy.springmvc.uadetector.configuration.EnableUADetector;
import com.github.mjeanroy.springmvc.uadetector.configuration.UACacheProvider;

@EnableWebMvc
@EnableUADetector(autowire = true, cache = UACacheProvider.AUTO)
public class MyAppConfiguration() {
}
```

Here is a spring mvc controller using arguments resolvers:

```java
package com.myApp;

import net.sf.uadetector.ReadableUserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class FirstController {

    /**
     * Return browser family.
     * See how an instance of ReadableUserAgent can be used as
     * a method parameter.
     *
     * @param userAgent Current user agent.
     * @return Browser Family.
     */
    @RequestMapping(value = "/ua/name", method = RequestMethod.GET)
    public String index(ReadableUserAgent userAgent) {
        return userAgent.getFamily().toString();
    }
}
```

Here is a spring mvc controller using autowiring:

```java
package com.myApp;

import net.sf.uadetector.ReadableUserAgent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class SecondController {

    @Autowire
    private ReadableUserAgent userAgent;

    /**
     * Return browser family.
     * See how an instance of ReadableUserAgent is automatically
     * autowired.
     *
     * @return Browser Family.
     */
    @RequestMapping(value = "/ua/name", method = RequestMethod.GET)
    public String index() {
        return userAgent.getFamily().toString();
    }
}
```