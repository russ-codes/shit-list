# The Shit List 

## Run
* Run the shit-list-service as a normal Springboot App
* To view the frontend hit `http://localhost:8080` in your browser

## Development
* Run the shit-list-service as a normal Springboot App
* Inside the shit-list-ui run `npm install` followed by `ng s`
* To view the frontend hit `http://localhost:4200` in your browser

### Enable continues delivery
* Set the following GitHub secrets
  * `DOCKER_USERNAME` - Your username to login to dockerhub.io
  * `DOCKER_PASSWORD` - Your password to login to dockerhub.io
  * `DOCKER_IMAGE` - What to call this image, should include your dockerhub username
* Remove 'disabled' from the 'release.yml' GitHub workflow
