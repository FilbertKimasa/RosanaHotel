<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>Hotel Rosana</title>
<base href="http://localhost:8080/RosanaHotel/">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=yes">
<meta name="description" content="Rosana Hotel">
<link href="assets/images/favicon.png" rel="shortcut icon" type="image/png">
<link href="assets/styles/main.css" type="text/css" rel="stylesheet" media="all">
<link href="assets/styles/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
</head>
<body>
<div id="global-container">
    <div id="header">
        <a id="logo" href="#" title="Logo"><img src="assets/images/logo.png" alt="logo"></a>
        <div id="nav-top">
            <a href="#">Home</a> | <a href="#aHotel" name="atoAbout" id="atoAbout">About Us</a> | <a href="#aContacts" name="atoContacts" id="atoContacts">Contacts</a> | <a href="gallery/">Gallery</a>
        </div>
    </div>
    <div id="banner">
        <div id="slideshow">
            <div class="ss-item fade-in">
                <div class="ss-img"><img src="assets/images/rh5.jpg" alt=""></div>
                <div class="slide-desc">An enough space for sitting and refreshing minds.</div>
            </div>
            <div class="ss-item fade-in">
                <div class="ss-img"><img src="assets/images/rh6.jpg" alt=""></div>
                <div class="slide-desc">A swimming pool is available.</div>
            </div>
            <div class="ss-item fade-in">
                <div class="ss-img"><img src="assets/images/rh1.jpg" alt=""></div>
                <div class="slide-desc">4 types of rooms. Book any of your choice.</div>
            </div>
            <div class="ss-item fade-in">
                <div class="ss-img"><img src="assets/images/rh7.jpg" alt=""></div>
                <div class="slide-desc">The exciting view of our hotel.</div>
            </div>
            <div id="slide-desc"></div>
            <div id="slide-controls">
                <span id="prev"><i class="fa fa-chevron-left"></i></span>
                <span id="play-pause"><i class="fa fa-pause"></i></span>
                <span id="next"><i class="fa fa-chevron-right"></i></span>
            </div>
        </div>
        <div id="nav-banner">
            <a href="#aHotel" name="atoHotel" id="atoHotel">Hotel</a>
            <a href="#aRooms" name="atoRooms" id="atoRooms">Rooms</a>
            <a href="#aOffers" name="atoOffers" id="atoOffers">Offers</a>
            <a href="book/">Book Now</a>
        </div>
    </div>
    <div id="content">
        <div class="content-box">
            <a name="aHotel" id="aHotel" href="#"></a>
            <div class="heading">Welcome to Hotel Rosana</div>
            <p>The area we are in is called India Street, Dar es Salaam, Tanzania.<br>A warm and personal calm will greet you as you walk into<br>your home-away-from-home in Dar es Salaam.<br>Offering authentic Tanzanian hospitably, the hotel originally commenced<br>operations in 2007, and stands as one of the city’s premier hotel for<br>both business and leisure travelers.</p>
            <p>Ideally situated in the heart of Dar es Salaam, Rosana Hotel offers a great<br>location with excellent value. It is strategically located in the city’s<br>dynamic business, financial, government offices, shopping, entertainment<br>and educational hubs and within walking distance<br>of connection by boat to Zanzibar.</p>
        </div>
        <div class="content-box">
            <a name="aRooms" id="aRooms" href="#"></a>
            <div class="heading">Rooms</div>
            <p>Our rooms offer a quiet place for guests to work from,<br>with free Wi-Fi access, mini-bar, tea and coffee<br>making facilities, air conditioning and satellite TV<br>for all those important work breaks.</p>
            <div id="accom-gallery">
                <div class="gallery-item">
                    <img src="assets/images/rh9.jpg" alt="">
                    <div class="glry-item-desc">
                        <div class="heading heading-caption">Standard Room</div>
                        <p>For an exceptional value, we have a couple of standard<br>rooms offering a comfortable accommodation.<br>The rooms are equipped with double beds, and<br>have private bathrooms.</p>
                        <p class="price-tag">$80/night</p>
                    </div>
                </div>
                <div class="gallery-item">
                    <img src="assets/images/rh8.jpg" alt="">
                    <div class="glry-item-desc">
                        <div class="heading heading-caption">Classic Room</div>
                        <p>These rooms equipped with one Double bed offer a<br>cozy simple setup for your stay.<br>No frills, just simple comfort.</p>
                        <p class="price-tag">$60/night</p>
                    </div>
                </div>
                <div class="gallery-item">
                    <img src="assets/images/rh1.jpg" alt="">
                    <div class="glry-item-desc">
                        <div class="heading heading-caption">Superior Room</div>
                        <p>Our Superior rooms have Queen beds and sofa beds.<br>They feature high ceilings with many original<br>architectural details and can sleep up to 3 people.</p>
                        <p class="price-tag">$100/night</p>
                    </div>
                </div>
                <div class="gallery-item">
                    <img src="assets/images/rh2.jpg" alt="">
                    <div class="glry-item-desc">
                        <div class="heading heading-caption">Family Suite</div>
                        <p>Our Family Suite is ideal for families and large groups.<br>The room includes one Queen bed, one double bed,<br>and a sofa bed, and can accommodate a total of 6 people.<br>You will enjoy the high ceilings, bright windows and original<br>architecture in this spacious suite.</p>
                        <p class="price-tag">$120/night</p>
                    </div>
                </div>
                <div class="clear-float"></div>
            </div>
        </div>
        <div class="content-box">
            <a name="aOffers" id="aOffers" href="#"></a>
            <div class="heading">Offers</div>
            <p>Book your stay of more than three days and<br>get one day for a free stay.<br>We also offer free breakfast in all days of<br>your stay plus a parking space for your &quot;ride&quot;.</p>
        </div>
        <div class="content-box">
            <a name="aContacts" id="aContacts" href="#"></a>
            <div class="heading">Contacts</div>
            <p>Mobile: +255 757 206 864</p>
            <p>Email: hotel.rosana@gmail.com</p><br>
            <p>Address:<br>P. O. Box 9432, Uhuru Road, Dar es Salaam</p>
        </div>
        <div class="content-box" id="footer">
            <img src="assets/images/logo.png" alt=""><br><br>copyright &#169; 2017 Hotel Rosana Ltd - all rights reserved
        </div>
    </div>
</div>
<script type="text/javascript">
var animateLinks = {
    "links": [
        { "node": document.links.atoAbout, "destNode": document.links.aHotel },
        { "node": document.links.atoContacts, "destNode": document.links.aContacts },
        { "node": document.links.atoHotel, "destNode": document.links.aHotel },
        { "node": document.links.atoRooms, "destNode": document.links.aRooms },
        { "node": document.links.atoOffers, "destNode": document.links.aOffers }
    ],
    "scrollTo": function (element, to, duration) {
        var start = element.offsetTop, change = to - start, currentTime = 0, increment = 20;

        var animateScroll = function(){
            currentTime += increment;
            var val = this.easeInOutQuad(currentTime, start, change, duration);
            element.scrollTop = val;
            if(currentTime < duration) {
                setTimeout(animateScroll, increment);
            }
        }.bind(this);
        animateScroll();
    },
    "easeInOutQuad": function(it, u, du, t) {
        it /= t/2;
        if (it < 1) return du/2*it*it + u;
        it--;
        return -du/2 * (it*(it-2) - 1) + u;
    },
    
    "init": function(scrollDuration) {
        this.links.forEach(function(e, i) {
            e.node.addEventListener("click", function(ev) {
                ev.preventDefault();
                this.scrollTo(document.documentElement, this.links[i].destNode.getBoundingClientRect().top + window.pageYOffset, scrollDuration);
            }.bind(this), false);
        }.bind(this));
    }
};

animateLinks.init(500);

var slideShow = {
    "ssWrapper": document.getElementById("slideshow"),
    "slideDesc": document.getElementById("slide-desc"),
    "slides": Array.prototype.slice.call(document.querySelectorAll("#slideshow .ss-item")).reverse(),
    "controls": document.getElementById("slide-controls"),
    "slideIndex": 0,
    "slideInterval": 5000,
    "slideTimer": null,
    "isPlaying": false,
    "gotoSlide": function(n) {
        this.slides.forEach(function(e, i) {
            if (i !== n) {
                e.style.display = "none";
            } else {
                this.slideIndex = n;
                e.style.display = "block";
                Array.prototype.slice.call(e.children).forEach(function(f, j) {
                    if (f.className.indexOf("slide-desc") !== -1) {
                        this.slideDesc.innerHTML = f.innerHTML;
                    }
                }.bind(this));
            }
        }.bind(this));
    },
    "gotoNextSlide": function() {
        this.slideIndex++;
        if (this.slideIndex > this.slides.length - 1) {
            this.slideIndex = 0;
         }
        this.gotoSlide(this.slideIndex);
    },
    "gotoPrevSlide": function() {
        this.slideIndex--;
        if (this.slideIndex < 0) {
            this.slideIndex = this.slides.length - 1;
         }
        this.gotoSlide(this.slideIndex);
    },
    "play": function() {
        if (!this.isPlaying) {
            this.isPlaying = true;
            this.slideTimer = window.setInterval(function() {
                this.gotoNextSlide();
            }.bind(this), this.slideInterval);
        }
    },
    "pause": function() {
        if (this.isPlaying && this.slideTimer) {
            window.clearInterval(this.slideTimer);
            this.isPlaying = false;
        }
    },
    "start": function() {
        this.gotoSlide(0);
        this.play(this.slideInterval);
        
        Array.prototype.slice.call(this.controls.children).forEach(function(e) {
            e.addEventListener("click", function() {
                if (e.id === "prev") {
                    this.gotoPrevSlide();
                } else if (e.id === "play-pause") {
                    if (this.isPlaying) {
                        this.pause();
                        e.innerHTML = '<i class="fa fa-play"></i>';
                    } else {
                        this.play(this.slideInterval);
                        e.innerHTML = '<i class="fa fa-pause"></i>';
                    }
                } else if (e.id === "next") {
                    this.gotoNextSlide();
                }
            }.bind(this), false);
        }.bind(this));
    }
};

slideShow.start();
</script>
</body>
</html>
