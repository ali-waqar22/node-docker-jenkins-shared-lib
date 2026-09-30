FROM node:26-alpine

WORKDIR /usr/app

COPY app/package*.json .

RUN npm install

COPY app/ .

EXPOSE 3000

CMD ["node", "server.js"]