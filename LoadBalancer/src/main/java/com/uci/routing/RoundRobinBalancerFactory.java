package com.uci.routing;

/**
 * Created by junm5 on 4/25/17.
 */
public class RoundRobinBalancerFactory implements ILoadBalancerFactory {

    @Override
    public ILoadBalancer createLoadBalancer() {
        return new RoundRobinBalancer();
    }
}
