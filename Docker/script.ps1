cd ..

cd ./frontend/Angular

docker run --rm --name angular-container -v "C:\Users\Iván\Documents\WEB\frontend\Angular":/angular -w /angular node:12.16.1 /bin/bash -c "npm install; ng build --prod --base-href /new/"

cp -R dist/Angular/* ../../backend/tenniShip/src/main/resources/static/new

cd ../..

docker run --rm -v "C:\Users\Iván\Documents\WEB\backend\tenniShip":/usr/src/project -w /usr/src/project maven:alpine mvn package

docker image build -t padawansurjc/tennishipapp -f Docker/Dockerfile .

docker push padawansurjc/tennishipapp

cd Docker