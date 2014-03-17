# clhue

This is a simple lightweight API client for
[Hue lighting systems](http://meethue.com/). It's the wand with which you shall
make magic.

## Name

Clue.

## Usage

The API of this library is mapped closely to the
[actual API](http://developers.meethue.com/gettingstarted.html). As such, look
at the parameters for the relevant API calls on the official API docs to figure
out what parameters you can change and pass and whatnot.

The first time you talk to your Hue bridge you'll need to create yourself an
account. Mosey on over to your bridge and click the big button in the
middle. Now hurry back over here because you only have 30 seconds to create your
user access token:

```clojure
(require '[me.raynes.clhue.config :as conf])

(def user (conf/auth-user {:address "someipaddress"} "description of user"))
```

Now user should either be an error because you were too slow after hitting the
link button, or it'll look like this:

```clojure
{:success {:username "154e465a3774cd172c0bc9c628d0d833"}}
```

Now that you've got a username, you should save it and always use it. You'll
likely now want to define a config map that looks like this:

```clojure
(def config {:address "someip"
             :user "154e465a3774cd172c0bc9c628d0d833"})
```

This is what you'll use to talk to your system now. For a more interesting
example, let's play with lights a bit:

```clojure
user> (lights/lights config)
{:1 {:name "Living door"}, :2 {:name "Living room middle"}, :3 {:name "Living room window"}}

user> (lights/light config 1)
{:state {:alert "select", :effect "none", :hue 36327, :on true, :sat 254, :xy [0.2883 0.2795], :colormode "hs", :reachable true, :ct 153, :bri 242}, :type "Extended color light", :name "Living door", :modelid "LCT001", :swversion "66009663", :pointsymbol {:1 "none", :2 "none", :3 "none", :4 "none", :5 "none", :6 "none", :7 "none", :8 "none"}}

user> (lights/light config 1 {:effect "colorloop"})
({:success {:/lights/1/state/effect "colorloop"}})
```

Perfect rave lighting! You can also use schedules to schedule events, groups to
control multiple lights at once (this API is in its infacy).

Have fun!

### Caveats

I don't like how Hue's API is outlined in some places. For example, the
attributes and state of a light are considered two different things for no
clear reason. As such, there is an API endpoint for changing 'attributes' and
another for changing 'state', even though the only attribute you can set on a
light is its name. I'm of the opinion that a light bulb's name is part of its
state. As such, I've made it so that if you try to update :name it shoots off
the proper request for that, and then another request for any other 'state' you
may have updated. I've done the same thing with the groups API, so keep this in mind.

## License

Copyright © 2014 Anthony Grimes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
