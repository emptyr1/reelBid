FROM endocode/storm:0.9.2
# web interface port
EXPOSE 8080

RUN /usr/bin/config-supervisord.sh ui
CMD /usr/bin/start-supervisor.sh
