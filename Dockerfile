# Dockerfile for java guide
FROM forax/java-guide

USER jovyan

# Launch the notebook server
ENV IJAVA_COMPILER_OPTS "--enable-preview --source=15"
WORKDIR $HOME/jupyter
CMD ["jupyter", "notebook", "--no-browser", "--ip", "0.0.0.0"]

