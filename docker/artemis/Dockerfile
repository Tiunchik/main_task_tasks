FROM bellsoft/liberica-openjdk-alpine:11

ENV ARTEMIS_USER artemis
ENV ARTEMIS_PASSWORD artemis
ENV ANONYMOUS_LOGIN false
ENV INPUT_OPTION true
ENV EXTRA_ARGS --http-host 0.0.0.0 --relax-jolokia

ENV BASE_TMPDIR /opt/artemis
ENV ARTEMIS_VERSION 2.21.0

ARG ARTEMIS_DIST_FILE_NAME=apache-artemis-${ARTEMIS_VERSION}-bin.tar.gz
ARG ARTEMIS_DIST=${BASE_TMPDIR}/${ARTEMIS_VERSION}
ARG CURL_OUTPUT=${BASE_TMPDIR}/${ARTEMIS_VERSION}/${ARTEMIS_DIST_FILE_NAME}

# Install curl and create dir
RUN apk update \
    && apk add curl \
    && mkdir -p ${BASE_TMPDIR}/${ARTEMIS_VERSION} \
    && touch ${BASE_TMPDIR}/${ARTEMIS_VERSION}/${ARTEMIS_DIST_FILE_NAME}

# Load tag file with artemis in two curl requests
RUN curl --progress-bar $(curl -s https://www.apache.org/dyn/closer.cgi\?preferred=true)activemq/activemq-artemis/${ARTEMIS_VERSION}/${ARTEMIS_DIST_FILE_NAME} --output ${CURL_OUTPUT}

# Unarchive tar file to ARTEMIS_DIST
RUN apk add tar \
    && tar xzf ${CURL_OUTPUT} --directory ${ARTEMIS_DIST}/ \
    && rm -rf ${CURL_OUTPUT}

# Create broker instanse with options
RUN $ARTEMIS_DIST/apache-artemis-${ARTEMIS_VERSION}/bin/artemis create \
    -user $ARTEMIS_USER --password $ARTEMIS_PASSWORD --silent $INPUT_OPTION \
    --allow-anonymous $ANONYMOUS_LOGIN $EXTRA_ARGS $ARTEMIS_DIST

# Expose ports
EXPOSE 8161 \
# JMX Exporter
    9404 \
# Port for CORE,MQTT,AMQP,HORNETQ,STOMP,OPENWIRE
    61616 \
# Port for HORNETQ,STOMP
    5445 \
# Port for AMQP
    5672 \
# Port for MQTT
    1883 \
#Port for STOMP
    61613

# Run brocker instance
CMD ${BASE_TMPDIR}/${ARTEMIS_VERSION}/bin/artemis run
