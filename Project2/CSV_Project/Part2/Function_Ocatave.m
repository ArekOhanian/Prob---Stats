##runs from 1 to 100
x = 1:1:100;
##regular function
y = 3 * (x .^ 2) + 7 * x + 3;
##here is the salting where we randomly add a number between -1000, and 1000
ySa = y + randi([-1000, 1000], size(y));
##here is the smoothing of the salted data
windowValue = 5;
ySm = movmean(ySa, windowValue);
##here is the plotting where the original is in blue and the salted is in red
 plot (x, y, 'b')
 hold on
 plot(x, ySa, 'r')
 hold on
 plot (x, ySm, 'g')
 hold on

 xlabel('x')
 ylabel('y')
 title("plots")
 legend('Original', 'Salted', 'Smoothed')
 grid on
