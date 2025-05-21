# Start from your base Jenkins agent image
# Use the correct Linux tag you settled on!
FROM jenkins/inbound-agent:latest-jdk17 

USER root

# Install Trivy (official recommended way for Debian/Ubuntu based images)
# See: https://aquasecurity.github.io/trivy/v0.52/getting-started/installation/
RUN apt-get update && \
    apt-get install -y wget apt-transport-https gnupg2 && \
    wget -qO - https://aquasecurity.github.io/trivy-repo/deb/public.key | apt-key add - && \
    echo "deb https://aquasecurity.github.io/trivy-repo/deb bookworm main" | tee -a /etc/apt/sources.list.d/trivy.list && \
    apt-get update && \
    apt-get install -y trivy

# --- ADD THESE LINES TO ENSURE JENKINS USER EXISTS ---
# These commands will ensure the 'jenkins' group and user exist,
# and their home directory permissions are correct.
# '|| true' ensures the command doesn't fail if the user/group already exist.
RUN groupadd -r jenkins || true && \
    useradd -r -g jenkins -m -d /home/jenkins -s /bin/bash jenkins || true && \
    chown -R jenkins:jenkins /home/jenkins
# --- END ADDITION ---

USER jenkins 
# Switch back to the jenkins user