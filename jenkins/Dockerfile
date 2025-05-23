# Start from your base Jenkins agent image
FROM jenkins/inbound-agent:latest-jdk17

USER root

# Install Trivy (official recommended way for Debian/Ubuntu based images)
# See: https://aquasecurity.github.io/trivy/v0.52/getting-started/installation/
RUN apt-get update && \
    apt-get install -y wget apt-transport-https gnupg2 && \
    wget -qO - https://aquasecurity.github.io/trivy-repo/deb/public.key | gpg --dearmor -o /etc/apt/keyrings/trivy-keyring.gpg && \ 
    # Use a specific keyring name
    echo "deb [signed-by=/etc/apt/keyrings/trivy-keyring.gpg] https://aquasecurity.github.io/trivy-repo/deb bookworm main" | tee -a /etc/apt/sources.list.d/trivy.list && \
    apt-get update && \
    apt-get install -y trivy

# --- DOCKER CLI INSTALLATION & USER PERMISSIONS ---
# Install necessary dependencies for Docker CLI
RUN apt-get update && \
    apt-get install -y apt-transport-https ca-certificates curl lsb-release && \
    rm -rf /var/lib/apt/lists/*

# Add Docker's official GPG key
RUN install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Set up the Docker repository
RUN echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
    $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

# Install Docker CLI packages
RUN apt-get update && \
    apt-get install -y docker-ce-cli && \
    rm -rf /var/lib/apt/lists/*

# Add 'jenkins' user to the 'docker' group
# This is crucial for fixing the "permission denied" error
RUN groupadd docker || true && usermod -aG docker jenkins

# --- END DOCKER CLI & PERMISSIONS ---


# --- Ensure JENKINS USER EXISTS (already in your file) ---
# These commands will ensure the 'jenkins' group and user exist,
# and their home directory permissions are correct.
# '|| true' ensures the command doesn't fail if the user/group already exist.
RUN groupadd -r jenkins || true && \
    useradd -r -g jenkins -m -d /home/jenkins -s /bin/bash jenkins || true && \
    chown -R jenkins:jenkins /home/jenkins
# --- END ADDITION ---

USER jenkins
# Switch back to the jenkins user