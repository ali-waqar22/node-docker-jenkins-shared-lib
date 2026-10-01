# Node.js CI/CD Pipeline with Docker & Jenkins Shared Library

This repository demonstrates a complete, enterprise-grade Continuous Integration and Continuous Deployment (CI/CD) workflow. It features a containerized Node.js application, automated versioning, and a fully modularized Jenkins pipeline utilizing a custom Jenkins Shared Library.

## 🚀 Features

* **Containerization:** Optimized `Dockerfile` using `node:26-alpine` with layer caching for rapid builds.
* **Jenkins Shared Library:** Pipeline logic is extracted into reusable Groovy scripts in the `vars/` directory, allowing other projects and teams to inherit the deployment logic.
* **Automated Versioning:** Automatically bumps the `package.json` patch version during the CI process.
* **Testing:** Integrated Jest testing stage that fails the build if application integrity is compromised.
* **Secure Credential Management:** Utilizes Jenkins credential store for secure GitHub PAT and Docker Hub authentication.
* **GitOps:** Commits the automated version bump back to the master branch using a secure remote URL.

## 📂 Repository Structure

```text
├── app/                        # Node.js application source code
│   ├── package.json            # App dependencies and scripts
│   ├── server.js               # Main application entry point
│   └── server.test.js          # Jest unit tests
├── vars/                       # Jenkins Shared Library global variables
│   ├── bumpVersion.groovy      # Script to bump npm version
│   ├── runTests.groovy         # Script to install deps and run Jest
│   ├── buildImage.groovy       # Script to build and push to Docker Hub
│   └── commitVersionUpdate.groovy # Script to commit and push to GitHub
├── Dockerfile                  # Container build instructions
└── Jenkinsfile                 # Declarative pipeline calling the shared library
```

## 🛠️ Prerequisites
To run this pipeline in your own environment, you will need:

* **Jenkins Server**: Hosted on a server (e.g., DigitalOcean droplet) running Docker.

* **Docker-out-of-Docker (DooD)**: Jenkins must be run with the host's Docker socket mounted (-v /var/run/docker.sock:/var/run/docker.sock) and Docker CLI installed inside the Jenkins container.

* **Node.js**: Installed inside the Jenkins container to execute npm commands.

* **Credentials**:

    * github: A GitHub Personal Access Token (PAT) saved in Jenkins as a Username with Password.

    * Dockerhub: Docker Hub credentials saved in Jenkins as a Username with Password.

## ⚙️ Jenkins Configuration
Before running the pipeline, configure this repository as a Global Shared Library in Jenkins:

* Navigate to Manage Jenkins > System > Global Pipeline Libraries.

* Add a new library named my-shared-library.

* Set the default version to master.

* Select Modern SCM > Git and provide the URL to this repository.

## 🔄 Pipeline Stages
The declarative Jenkinsfile executes the following parameterized stages via the Shared Library:

* **Bump Version**: Navigates into the app/ directory, runs npm version patch, and extracts the new version number.

* **Run Tests**: Installs npm dependencies and executes the Jest test suite.

* **Build and Push Docker Image**: Builds the Docker image from the root directory using the bumped version and Jenkins build number as a tag, then pushes it to Docker Hub.

* **Commit Version Update**: Stages the modified app/package.json, commits the version bump, and securely pushes the changes back to GitHub.

## 🚢 Manual Server Deployment
Once the pipeline successfully pushes the image to Docker Hub, you can deploy it to your server using the following commands:


### Log in to Docker Hub (if pulling a private repository)
    docker login

### Run the newly built image (replace `<version>` with the pipeline output tag)
    docker run -p 3000:3000 -d aliwaqarbulc/node-docker-jenkins-shared-lib:<version>

### Ensure the firewall allows traffic on port 3000
    ufw allow 3000/tcp
Access the application by navigating to http://`<your-server-ip>`:3000 in your browser.

## Developed by Muhammad Ali Waqar.