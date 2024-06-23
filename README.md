# SprintLux - The last linux speedrun timer you'll ever need

## Prerequisites
- Install [Gradle](https://gradle.org/)
- Install the JVM (or have some way to run .jar files)
- Join the `input` group on your computer: `sudo gpasswd -a input $YOUR_USERNAME`

## Building and running

Run `gradle build` to build the application. This will generate your jar file in `build/libs/sprintlux.jar`.

You can also run `gradle run` to run the application.

## Testing

Not this time, sorry :) Testing a GUI application is hard, especially when you're doing stuff like reading from /dev/input which is a low-level system operation that's hard to mock.

## Licensing

Copyright (c) rayyaw, 2024. As of right now, SprintLux has all rights reserved. Check back later for updates.