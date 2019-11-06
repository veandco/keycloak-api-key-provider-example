# Keycloak API Key Provider example

This is a simple API Key generator that creates API Key when user
registers or is created by the admin.

The API key will be put under the user's `attributes` as `api-key`.

## Requirements

Maven needs to be installed first.

## Installation

1. Run `mvn install` in the directory.
2. Copy `.jar` file from `target` directory into Keycloak's
  `standalone/deployments` directory. Keycloak will hot-reload.
3. It should be done! Now you can try to register or add a user and
   check for the `api-key` attribute.

## Credits

Most of the work here came from [zak905's repo](https://github.com/zak905/keycloak-api-key-demo). All I did is simplify
the example to do only the things that I need which is API key
generation on Keycloak (without Docker and the web services).
