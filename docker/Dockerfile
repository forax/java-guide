# Dockerfile for Jupyter Notebook
FROM openjdk:15-jdk-buster

RUN apt-get update
RUN apt-get install -y python3-pip

# add requirements.txt, written this way to gracefully ignore a missing file
COPY . .
RUN ([ -f docker/requirements.txt ] \
    && pip3 install --no-cache-dir -r docker/requirements.txt) \
        || pip3 install --no-cache-dir jupyter jupyterlab

USER root

# Download the kernel release
RUN curl -L https://github.com/SpencerPark/IJava/releases/download/v1.3.0/ijava-1.3.0.zip > ijava-kernel.zip

# Unpack, patch kernel.json and install the kernel
RUN unzip ijava-kernel.zip -d ijava-kernel \
  && cp docker/kernel.json ijava-kernel/java \
  && cd ijava-kernel \
  && python3 install.py --sys-prefix

# Set up the user environment
ENV NB_USER jovyan
ENV NB_UID 1000
ENV HOME /home/$NB_USER

RUN adduser --disabled-password \
    --gecos "Default user" \
    --uid $NB_UID \
    $NB_USER

COPY . $HOME
RUN chown -R $NB_UID $HOME

USER $NB_USER

# Launch the notebook server
ENV IJAVA_COMPILER_OPTS "--enable-preview --source=15"
WORKDIR $HOME
CMD ["jupyter", "notebook", "--no-browser", "--ip", "0.0.0.0"]

